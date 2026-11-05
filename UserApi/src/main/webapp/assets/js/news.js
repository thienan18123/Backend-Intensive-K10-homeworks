const $ = (id) => document.getElementById(id);
const getToken = () => sessionStorage.getItem("jwt");


async function getJson(path) {
    const resp = await fetch("api" + path);
    if (resp.status === 404) return null;
    if (!resp.ok) throw new Error("Server error " + resp.status);
    return resp.json();
}


let refreshing = null;    


function refreshToken() {
    if (!refreshing) {
        refreshing = fetch("api/auth/refresh", { method: "POST" })
            .then((r) => (r.ok ? r.json() : null))
            .then((data) => {
                if (data) sessionStorage.setItem("jwt", data.token);
                else sessionStorage.removeItem("jwt");
                return Boolean(data);
            })
            .catch(() => false)
            .finally(() => { refreshing = null; });
    }
    return refreshing;
}


async function ensureToken() {
    return getToken() ? true : refreshToken();
}


async function authFetch(path, options = {}) {
    const send = () => fetch("api" + path, {
        ...options,
        headers: { ...(options.headers || {}), "Authorization": "Bearer " + getToken() }
    });
    let resp = await send();
    if (resp.status === 401 && await refreshToken()) {
        resp = await send();                                
    }
    if (resp.status === 401) sessionStorage.removeItem("jwt");
    return resp;
}


function el(tag, text) {
    const e = document.createElement(tag);
    if (text !== undefined) e.textContent = text;
    return e;
}
function link(href, text) {
    const a = el("a", text);
    a.href = href;
    return a;
}
function button(text, onClick) {
    const b = el("button", text);
    b.type = "button";
    b.addEventListener("click", onClick);
    return b;
}
const categoryLink = (c) => link("category.html?id=" + encodeURIComponent(c.id), c.name);
const articleLink = (a) => link("article.html?id=" + encodeURIComponent(a.id), a.title);

function articleList(articles) {
    const ul = el("ul");
    for (const a of articles) {
        const li = el("li");
        li.append(articleLink(a), el("br"), a.summary || "");
        ul.append(li);
    }
    if (articles.length === 0) ul.append(el("li", "No articles yet."));
    return ul;
}


async function renderAuthBar() {
    const bar = $("authbar");
    if (await ensureToken()) {
        const resp = await authFetch("/me");
        if (resp.ok) {
            const me = await resp.json();
            const logout = button("LOGOUT", async () => {
                await fetch("api/auth/logout", { method: "POST" });  
                sessionStorage.removeItem("jwt");
                location.href = "home.html";
            });
            const logoutAll = button("Log out everywhere", async () => {
                await authFetch("/sessions", { method: "DELETE" });   
                sessionStorage.removeItem("jwt");
                location.href = "home.html";
            });
            bar.replaceChildren("Hello ", el("b", me.name), " / ", link("saved.html", "Saved list"),
                                " / ", logout, " ", logoutAll);
            return;
        }
    }
    bar.replaceChildren(link("register.html", "REGISTER"), " / ", link("login.html", "LOGIN"));
}


async function showHome() {
    const categories = await getJson("/news/home");
    if (!categories) throw new Error("Cannot load news (API /api/news/home not found)");   
    const box = $("content");
    box.replaceChildren(el("h1", "News"));
    for (const c of categories) {
        const h2 = el("h2");
        h2.append(categoryLink(c));
        box.append(h2, articleList(c.articles));
    }
}
async function showCategory(id) {
    const data = await getJson("/news/categories/" + encodeURIComponent(id));
    if (!data) return notFound();
    document.title = data.category.name;

    const crumbs = el("p");
    crumbs.append(link("home.html", "Home"));
    if (data.parent) crumbs.append(" › ", categoryLink(data.parent));

    const box = $("content");
    box.replaceChildren(crumbs, el("h1", data.category.name));
    if (data.subCategories.length > 0) {
        const subs = el("p", "Sub-categories: ");
        data.subCategories.forEach((s, i) => subs.append(i ? " | " : "", categoryLink(s)));
        box.append(subs);
    }
    box.append(articleList(data.articles));
}

async function showArticle(id) {
    const data = await getJson("/news/articles/" + encodeURIComponent(id));
    if (!data) return notFound();
    const a = data.article;
    document.title = a.title;

    const crumbs = el("p");
    crumbs.append(link("home.html", "Home"), " › ", categoryLink(data.category));

    const box = $("content");
    box.replaceChildren(crumbs, el("h1", a.title));
	if (await ensureToken()) box.append(await saveButton(a.id));      
    box.append(el("p", a.summary || ""));
    for (const paragraph of (a.content || "").split(/\n\s*\n/)) { 
        if (paragraph.trim()) box.append(el("p", paragraph.trim()));
    }
}


async function saveButton(articleId) {
    const url = "/saved/" + encodeURIComponent(articleId);
    const resp = await authFetch(url);
    if (resp.status === 401) return el("span");            
    let saved = resp.ok && (await resp.json()).saved;

    const b = button("", async () => {
        b.disabled = true;                                   
        const r = await authFetch(url, { method: saved ? "DELETE" : "PUT" });
        if (r.ok) saved = !saved;
        b.disabled = false;
        paint();
    });
    const paint = () => { b.textContent = saved ? "★ Saved (click to remove)" : "☆ Save"; };
    paint();
    return b;
}

async function showSaved() {
    document.title = "Saved list";
    const box = $("content");
    if (!(await ensureToken())) {
        const p = el("p", "Please ");
        p.append(link("login.html", "log in"), " to see your saved list.");
        box.replaceChildren(el("h1", "Saved list"), p);
        return;
    }
    const resp = await authFetch("/saved");
    if (resp.status === 401) return showSaved();             
    if (!resp.ok) throw new Error("Server error " + resp.status);
    const articles = await resp.json();

    const ul = el("ul");
    for (const a of articles) {
        const li = el("li");
        const remove = button("Remove", async () => {
            const r = await authFetch("/saved/" + encodeURIComponent(a.id), { method: "DELETE" });
            if (r.ok) li.remove();
            if (!ul.children.length) ul.append(el("li", "Nothing saved yet."));
        });
        li.append(articleLink(a), " ", remove, el("br"), a.summary || "");
        ul.append(li);
    }
    if (articles.length === 0) ul.append(el("li", "Nothing saved yet."));
    box.replaceChildren(link("home.html", "« Home"), el("h1", "Saved list"), ul);
}

function notFound() {
    document.title = "Not found";
    $("content").replaceChildren(el("h1", "404 - Not found"), link("home.html", "« Home"));
}


renderAuthBar();
const pageId = new URLSearchParams(location.search).get("id");
const pages = {
    home: showHome,
    category: () => showCategory(pageId),
    article: () => showArticle(pageId),
    saved: showSaved
};
pages[document.body.dataset.page]().catch((e) => { $("content").textContent = e.message; });
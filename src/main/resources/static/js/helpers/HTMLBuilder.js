class HTMLBuilder {
  createElement(tag, id = "", cls = "") {
    const el = document.createElement(tag);
    if (id) el.id = id;
    if (cls) el.className = cls;
    return el;
  }
  createDiv(id = "", cls = "") {
    const div = this.createElement("div", id, cls);
    return div;
  }
  createParagraph(text, id = "", cls = "") {
    const p = this.createElement("p", id, cls);
    p.textContent = text;
    return p;
  }
  createButton(label, id = "", cls = "") {
    const btn = this.createElement("button", id, cls);
    btn.textContent = label;
    return btn;
  }

  appendElement(element, parentId) {
    const parent = document.getElementById(parentId);
    if (parent) parent.append(element);
  }
}

export default HTMLBuilder;

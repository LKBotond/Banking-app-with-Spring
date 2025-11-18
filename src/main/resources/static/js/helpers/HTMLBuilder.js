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

  buildAccount(account, index) {
    const accountIdx = "account" + index;
    const holder = this.createDiv(accountIdx, "account");
    const id = this.createParagraph(account.accountID, "id");
    const balance = this.createParagraph(account.balance, "balance");
    const transferBTN = this.createButton("Transfer", "transfer"+index, "nav");
    transferBTN.dataset.accountId = account.accountID;
    const depositBTN = this.createButton("Deposit", "deposit"+index, "nav");
    depositBTN.dataset.accountId = account.accountID;
    const detailsBTN = this.createButton("Details", "details"+index, "nav");
    detailsBTN.dataset.accountId = account.accountID;

    holder.appendChild(id);
    holder.appendChild(balance);
    holder.appendChild(transferBTN);
    holder.appendChild(depositBTN);
    holder.appendChild(detailsBTN);

    return holder;
  }
}

export default HTMLBuilder;

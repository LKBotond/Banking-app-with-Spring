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

  createSpan(content, id = "", cls = "") {
    const span = this.createElement("span", id, cls);
    span.textContent = content;
    return span;
  }

  appendElement(element, parentId) {
    const parent = document.getElementById(parentId);
    if (parent) parent.append(element);
  }

  registerEventListener(target, event, callback) {
    target.addEventListener(event, callback);
  }

  buildAccount(account, index) {
    const accountIdx = "account" + index;
    const holder = this.createDiv(accountIdx, "account" + accountIdx);
    holder.dataset.accountId = account.accountID;
    const id = this.createParagraph(
      "Account number: " + account.accountID,
      account.accountID
    );
    const balanceParagraph = this.createParagraph(
      "Your balance: ",
      "paragraphForBalance" + accountIdx
    );
    const balance = this.createSpan(account.balance, "balance" + accountIdx);
    const transferBTN = this.createButton(
      "Transfer",
      "transfer" + index,
      "nav"
    );
    transferBTN.dataset.accountId = account.accountID;
    const depositBTN = this.createButton("Deposit", "deposit" + index, "nav");
    depositBTN.dataset.accountId = account.accountID;
    const withdrawalBTN = this.createButton(
      "Withdraw",
      "withdraw" + index,
      "nav"
    );
    withdrawalBTN.dataset.accountId = account.accountID;
    const detailsBTN = this.createButton("Details", "details" + index, "nav");
    depositBTN.dataset.accountId = account.accountID;

    holder.appendChild(id);
    holder.appendChild(balanceParagraph);
    balanceParagraph.appendChild(balance);
    holder.appendChild(transferBTN);
    holder.appendChild(depositBTN);
    holder.appendChild(withdrawalBTN);
    holder.appendChild(detailsBTN);

    return holder;
  }

  rebuildAccount(account) {
    console.log(account);
    const target = document.querySelector(
      `div[data-account-id="${account.accountID}"]`
    );
    console.log(target);
    if (!target) return;
    const balance = target.querySelector('[id^="balance"]');
    balance.innerHTML = account.balance;
    console.log("Balance updated DOM:", balance.innerHTML);
  }
}

export default HTMLBuilder;

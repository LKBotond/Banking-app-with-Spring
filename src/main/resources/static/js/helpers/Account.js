class Account {
  constructor(id, balance) {
    this.id = id;
    this.balance = balance;
  }
  getId() {
    return this.id;
  }
  getBalance() {
    return this.balance;
  }
}
export default Account;

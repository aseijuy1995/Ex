class MockEmailUtil : IEmailUtil {
    var receiveEmail: String? = null

    override fun sendCustomer(email: String) {
        receiveEmail = email
    }
}
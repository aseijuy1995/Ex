class Order {

    fun insertOrder(email: String, quantity: Int, price: Int, emailUtil: IEmailUtil) {
        val weather = Weather()
        val umbrella = Umbrella()

        umbrella.totalPrice(weather, quantity, price)

        emailUtil.sendCustomer(email)
    }

}
class Umbrella {

    fun totalPrice(iWeather: IWeather,quantity: Int, price: Int): Int {
        val isSunny = iWeather.isSunny()
        var totalPrice = quantity * price
        if (isSunny) {
            totalPrice = (totalPrice * 0.9).toInt()
        }
        return totalPrice
    }
}
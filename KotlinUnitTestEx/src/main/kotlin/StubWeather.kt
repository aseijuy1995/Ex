class StubWeather : IWeather {

    var fakeIsSunny = false

    override fun isSunny(): Boolean {
        return fakeIsSunny
    }
}
package singleton

fun main(args: Array<String>) {
    val singleton1 = Singleton()
    val singleton2 = Singleton()
    val singletonEx1 = SingletonEx()
    val singletonEx2 = SingletonEx()
    val singletonHungryMan1 = SingletonHungryMan.get()
    val singletonHungryMan2 = SingletonHungryMan.get()
    val singletonLazyMan1 = SingletonLazyMan.get()
    val singletonLazyMan2 = SingletonLazyMan.get()
    val singletonDCL1 = SingletonDCL.get()
    val singletonDCL2 = SingletonDCL.get()
    val singletonEnum1 = SingletonEnum.INSTANCE
    val singletonEnum2 = SingletonEnum.INSTANCE
    val singletonObject1 = SingletonObject
    val singletonObject2 = SingletonObject

    println(singleton1)
    println(singleton2)
    println(singletonEx1)
    println(singletonEx2)
    //餓漢
    println(singletonHungryMan1)
    println(singletonHungryMan2)
    //懶漢
    println(singletonLazyMan1)
    println(singletonLazyMan2)
    //DCL
    println(singletonDCL1)
    println(singletonDCL2)
    //Enum
    println(singletonEnum1)
    println(singletonEnum2)
    //kotlin-object
    println(singletonObject1)
    println(singletonObject2)

}
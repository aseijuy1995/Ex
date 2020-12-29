import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.atLeast
import org.mockito.MockitoAnnotations
import org.mockito.internal.verification.VerificationModeFactory.atMost
import org.mockito.internal.verification.VerificationModeFactory.times

class OrderTest {
    private lateinit var order: Order

    @Mock
    lateinit var mockEmailUtil: IEmailUtil

    @BeforeEach
    fun before() {
        order = Order()
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun insertOrderTest() {
        val email = "123@gmail.com"
        val mockEmailUtil = MockEmailUtil()

        order.insertOrder(email, 1, 100, mockEmailUtil)
        assertEquals(email, mockEmailUtil.receiveEmail)
    }

    @Test
    fun inertOrder2Test() {
        val email = "123@gmail.com"
        val mockEmailUtil = Mockito.mock(IEmailUtil::class.java)
        order.insertOrder(email, 1, 100, mockEmailUtil)

        Mockito.verify(mockEmailUtil).sendCustomer(email)
        Mockito.verify(mockEmailUtil, times(1)).sendCustomer(email)
//        Mockito.verify(mockEmailUtil,never()).sendCustomer(email)
        Mockito.verify(mockEmailUtil, atLeast(1)).sendCustomer(email)
        Mockito.verify(mockEmailUtil, atMost(1)).sendCustomer(email)
    }

    @Test
    fun insertOrderWithMockk() {
        val order = Order()
        val mockEmailUtil = mockk<IEmailUtil>()
        val slot = slot<String>()

        val email = "123@gmail.com"
        every { mockEmailUtil.sendCustomer(capture(slot)) }.answers {
            assertEquals(email, slot.captured)
        }

        order.insertOrder(email, 1, 100, mockEmailUtil)
    }
}
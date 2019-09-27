package ostrat
import utest._

/** Just a dummy test object to keep the build tools happy. */
object OstratTest  extends TestSuite {
  val tests = Tests
  {
    'Dummy1 -
    {
      true ==> true
    }
  }
}

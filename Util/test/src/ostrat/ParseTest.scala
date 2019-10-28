package ostrat
import utest._

object ParseTest extends TestSuite
{
  val tests = Tests
  {
    val settingStr = "x = -5; y = 7; true"

    'Test1
    {
      "4".findType[Int] ==> Good(4)
     //-4".findType[Int] ==> Good(-4)
     // settingStr.findSett[Int]("x") ==> Good(-5)
     // settingStr.findSett[Int]("y") ==> Good(7)
      //settingStr.findIntSett("y") ==> Good(7)
     // settingStr.findType[Boolean] ==> Good(true)
    }
  }
}

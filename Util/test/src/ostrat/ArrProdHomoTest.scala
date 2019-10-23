package ostrat
import utest._

object ArrProdHomoTest  extends TestSuite
{
  case class Mine(a: Double, b: Double) extends ProdDbl2
  { def _1: Double = a
    def _2: Double = b
    override def canEqual(that: Any): Boolean = that match
    { case Mine(_, _) => true
      case _ => false
    }
  }

  class Mines(val array: Array[Double]) extends AnyVal with ArrProdDbl2[Mine]
  { type ThisT = Mines
    def typeStr = "Mines"
    def unsafeFromArray(array: Array[Double]): Mines = new Mines(array)
    override def elemBuilder(d1: Double, d2: Double): Mine = Mine(d1, d2)
  }
  val tests = Tests
  {
    val ints1 = Ints(1, 2, 3, 4)

    'test1 -
      { ints1(3) ==> 4

      }


  }
}

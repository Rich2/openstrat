package ostrat

trait EMonBase[+T]
trait GoodBase[+T] extends EMonBase[T]
trait BadBase[+T] extends EMonBase[T]

trait EMonBuild[T]
{
  type EMonT <: EMonBase[T]
  type GoodT <: EMonT with GoodBase[T]
  type BadT <: EMon[T] with BadBase[T]
  def apply(value: T): GoodT
}

object EMonBuild
{
  implicit def eMonImplicit[T](implicit special: Not[EMonBuild[T]]): EMonBuild[T] = ???
}

trait EMonInt extends EMonBase[Int]
case class GoodInt(value: Int) extends EMonInt with GoodBase[Int]
case class BadInt(errs: Refs[String]) extends EMonInt with BadBase[Int]
object NoneInt extends BadInt(Refs())

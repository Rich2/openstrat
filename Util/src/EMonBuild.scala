package ostrat

trait EMonBase[+A]
{
  def mMap[B](f: A => B)(implicit build: EMonBuild[B]): build.EMonT
  def errs: Strings
}

trait GoodBase[+A] extends EMonBase[A]
{ def value: A
  def errs: Strings = Refs()
}

trait BadBase[+T] extends EMonBase[T]
{
  def errs: Refs[String]
}

trait EMonBuild[T]
{
  type EMonT <: EMonBase[T]
  type GoodT <: EMonT with GoodBase[T]
  type BadT <: EMonT with BadBase[T]
  def apply(value: T): GoodT
  def newBad(errs: Refs[String]): BadT
}

object EMonBuild
{
  implicit def eMonImplicit[T]: EMonBuild[T] = new EMonBuild[T]
  { override type EMonT = EMon[T]
    override type GoodT = Good[T]
    override type BadT = Bad[T]
    override def apply(value: T): Good[T] = Good(value)
    override def newBad(errs: Refs[String]): Bad[T] = new Bad(errs)
  }

  implicit val intImplicit: EMonBuild[Int] = new EMonBuild[Int]
  { override type EMonT = EMonInt
    override type GoodT = GoodInt
    override type BadT = BadInt
    override def apply(value: Int): GoodInt = GoodInt(value)
    override def newBad(errs: Refs[String]): BadInt = new BadInt(errs)
  }
}

trait EMonInt extends EMonBase[Int]
{ def mMap[B](f: Int => B)(implicit build: EMonBuild[B]): build.EMonT
}

case class GoodInt(value: Int) extends EMonInt with GoodBase[Int]
{
  override def mMap[B](f: Int => B)(implicit build: EMonBuild[B]): build.EMonT = build(f(value))
}
case class BadInt(errs: Refs[String]) extends EMonInt with BadBase[Int]
{
  override def mMap[B](f: Int => B)(implicit build: EMonBuild[B]): build.EMonT = build.newBad(errs)
}
object NoneInt extends BadInt(Refs())

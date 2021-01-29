/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Trait for Show for product of 2 logical elements. This trait is implemented directly by the type in question, unlike the corresponding [[Show2T]]
 *  trait which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred
 *  over [[Show2T]]. */
trait Show2[A1, A2] extends Any with ShowProduct with Prod2[A1, A2]
{ def name1: String
  def name2: String
  def elemNames: Strings = Strings(name1, name2)
  implicit def ev1: ShowT[A1]
  implicit def ev2: ShowT[A2]
  def elemTypeNames: Strings = Strings(ev1.typeStr, ev2.typeStr)
  def strs(way: Show.Way, decimalPlaces: Int): Strings = Strings(ev1.showT(el1, way, decimalPlaces), ev2.showT(el2, way, decimalPlaces))
}

/** Trait for Show for product of 2 Ints. This trait is implemented directly by the type in question, unlike the corresponding [[Show2IntsT]]
 *  trait which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred
 *  over [[Show2T]]. */
trait Show2Ints extends Any with Show2[Int, Int]
{ final override implicit def ev1: ShowT[Int] = ShowT.intPersistImplicit
  final override implicit def ev2: ShowT[Int] = ShowT.intPersistImplicit
  final override def syntaxdepth: Int = 2
}

/** Trait for Show for product of 2 Doubles. This trait is implemented directly by the type in question, unlike the corresponding [[Show2DblsT]]
 *  trait which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred
 *  over [[Show2T]]. */
trait Show2Dbls extends Any with Show2[Double, Double] with Dbl2Elem with Approx[Double]
{ final override implicit def ev1: ShowT[Double] = ShowT.doublePersistImplicit
  final override implicit def ev2: ShowT[Double] = ShowT.doublePersistImplicit
  final override def syntaxdepth: Int = 2
}

trait Show2erT[A1, A2, R <: Show2[A1, A2]] extends ShowT[R]
{
  /** Provides the standard string representation for the object. Its called ShowT to indicate this is a type class method that acts upon an object
   * rather than a method on the object being shown. */
  override def strT(obj: R): String = obj.str

  override def showT(obj: R, way: Show.Way, decimalPlaces: Int): String = obj.show(way, decimalPlaces)

  /** Simple values such as Int, String, Double have a syntax depth of one. A Tuple3[String, Int, Double] has a depth of 2. Not clear whether this
   * should always be determined at compile time or if sometimes it should be determined at runtime. */
  override def syntaxDepthT(obj: R): Int = obj.syntaxdepth
}

object Show2erT
{
  def apply[A1, A2, R<: Show2[A1, A2]](typeStrIn: String): Show2erT[A1, A2, R] = new Show2erT[A1, A2, R]
  { override def typeStr: String = typeStrIn
    override def showT(obj: R, way: Show.Way, decimalPlaces: Int): String = obj.show(way, decimalPlaces)
  }
}

/** Show type class for 2 parameter case classes. */
trait Show2T[A1, A2, R] extends ShowProductT[R]
{
  def typeStr: String
  def name1: String
  def fArg1: R => A1
  def name2: String
  def fArg2: R => A2
  def opt2: Option[A2]
  def opt1: Option[A1]
  implicit def ev1: ShowT[A1]
  implicit def ev2: ShowT[A2]
  final override def syntaxDepthT(obj: R): Int = ev1.syntaxDepthT(fArg1(obj)).max(ev2.syntaxDepthT(fArg2(obj))) + 1

  override def strs(obj: R, way: Show.Way, decimalPlaces: Int): Strings =
    Strings(ev1.showT(fArg1(obj), way, decimalPlaces), ev2.showT(fArg2(obj), way, decimalPlaces))
}

object Show2T
{
  def apply [A1, A2, R](typeStrIn: String, name1In: String, fArg1In: R => A1, name2In: String, fArg2In: R => A2, opt2In: Option[A2] = None,
                        opt1In: Option[A1] = None)(implicit ev1In: ShowT[A1], ev2In: ShowT[A2]): Show2T[A1, A2, R] = new Show2T[A1, A2, R]
  {
    override def typeStr: String = typeStrIn
    override def name1: String = name1In
    override def fArg1: R => A1 = fArg1In
    override def name2: String = name2In
    override def fArg2: R => A2 = fArg2In
    override implicit def ev1: ShowT[A1] = ev1In
    override implicit def ev2: ShowT[A2] = ev2In
    val opt2: Option[A2] = opt2In
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  }
}

class Show2TExtensions[A1, A2, -T](ev: Show2T[A1, A2, T], thisVal: T)
{
  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowT]] type class instances. */
  def show2(way: Show.Way = Show.Standard, way1: Show.Way = Show.Standard, places1: Int = -1, way2: Show.Way = Show.Standard, places2: Int = -1): String = ???
}

/** A trait for making quick ShowT instances for products of 2 Doubles. */
trait Show2DblsT[R <: Show2Dbls] extends Show2erT[Double, Double, R]

object Show2Dbls
{ /** Factory apply method for creating quick ShowT instances for products of 2 Doubles. */
  def apply[R <: Show2Dbls](typeStrIn: String): Show2DblsT[R] = new Show2DblsT[R]()
  { val typeStr: String = typeStrIn
  }
}

/** A trait for making quick ShowT instances for products of 2 Ints. */
trait Show2IntsT[R <: Show2Ints]extends Show2erT[Int, Int, R]

object Show2IntsT
{ /** Factory apply method for creating quick ShowT instances for products of 2 [[Int]]s. */
  def apply[R <: Show2Ints](typeStrIn: String): Show2IntsT[R] = new Show2IntsT[R]()
  { val typeStr: String = typeStrIn
  }
}

/** Persistence class for product 2 type class. It ShowTs and UnShows objects with 2 logical parameters. */
class Persist2[A1, A2, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2, val newT: (A1, A2) => R,
  val opt2: Option[A2] = None, opt1In: Option[A1] = None)(implicit ev1In: Persist[A1], ev2In: Persist[A2]) extends Show2T[A1, A2, R] with
  PersistProduct[R]
{
  val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  override implicit def ev1: ShowT[A1] = ev1In
  override implicit def ev2: ShowT[A2] = ev2In
}

/** Factory object for Persist product 2 type class */
object Persist2
{ def apply[A1, A2, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, newT: (A1, A2) => R,
                       opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2], eq1: EqT[A1], eq2: EqT[A2]): Persist2[A1, A2, R] =
  new Persist2(typeStr, name1, fArg1, name2, fArg2, newT, opt2, opt1)(ev1, ev2)
}

/*class Persist2er[A1, A2, R <: Show2[A1, A2]](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2, val newT: (A1, A2) => R,
   val opt2: Option[A2] = None, opt1In: Option[A1] = None)(implicit ev1In: Persist[A1], ev2In: Persist[A2]) extends Show2erT[A1, A2, R] with
  PersistProduct[R]
{
  val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  implicit def ev1: ShowT[A1] = ev1In
  implicit def ev2: ShowT[A2] = ev2In
}*/

/** Persistence class for case classes consisting of 2 Int parameters. */
class Persist2Ints[R](typeStr: String, name1: String, fArg1: R => Int, name2: String, fArg2: R => Int, newT: (Int, Int) => R) extends
  Persist2[Int, Int, R](typeStr, name1, fArg1, name2, fArg2, newT)

/** Persistence class for case classes consisting of 2 Double parameters. */
class Persist2Dbls[R](typeStr: String, name1: String, fArg1: R => Double, name2: String, fArg2: R => Double, newT: (Double, Double) => R) extends
  Persist2[Double, Double, R](typeStr, name1, fArg1, name2, fArg2, newT)

/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A base trait for [[Show3T]] and [[UnShow3]], declares the common properties of name1 - 3 and opt1 - 3. */
trait ShowSelf3[A1, A2, A3] extends Any with ShowSelf2[A1, A2]
{ /** 1st parameter name. */
  def name3: String

  /** The optional default value for parameter 2. */
  def opt3: Option[A3]
}

/** Trait for [[Show]] for a product of 3 logical elements. This trait is implemented directly by the type in question, unlike the corresponding
 *  [[ShowEq3T]] trait which externally acts on an object of the specified type to create its String representations. For your own types it is better to
 *  inherit from Show3 and then use [[Show3ElemT]] or [[Persist3ElemT]] to create the type class instance for ShowT. The [[Show3ElemT]] or
 *  [[Persist3Elem]] class will delegate to Show3 for some of its methods. It is better to use Show3 to override toString method than delegating the
 *  toString override to a [[ShowEq3T]] instance. */
trait Show3[A1, A2, A3] extends Any with ShowProduct with ShowSelf3[A1, A2, A3]
{ override def opt1: Option[A1] = None
  override def opt2: Option[A2] = None
  override def opt3: Option[A3] = None

  /** Element 1 of this 3 element Show product. */
  def show1: A1

  /** Element 2 of this 3 element Show product. */
  def show2: A2

  /** Element 3 of this 3 element Show product. */
  def show3: A3

  /** The ShowT type class instance for the 1st element of this 3 element Show product. */
  def showT1: ShowT[A1]

  /** The ShowT type class instance for the 2nd element of this 3 element Show product. */
  def showT2: ShowT[A2]

  /** The ShowT type class instance for the 3rd element of this 3 element Show product. */
  def showT3: ShowT[A3]

  def elemNames: Strings = Strings(name1, name2, name3)
  def elemTypeNames: Strings = Strings(showT1.typeStr, showT2.typeStr, showT3.typeStr)
  def showElemStrs(way: ShowStyle, decimalPlaces: Int): Strings = Strings(showT1.showT(show1, way, decimalPlaces, 0), showT2.showT(show2, way, decimalPlaces, 0),
    showT3.showT(show3, way, decimalPlaces, 0))
}

trait ShowDbl3 extends Any with Show3[Double, Double, Double]
{ final override def syntaxDepth: Int = 2
  final override implicit def showT1: Persist[Double] = ShowT.doublePersistImplicit
  final override implicit def showT2: Persist[Double] = ShowT.doublePersistImplicit
  final override implicit def showT3: Persist[Double] = ShowT.doublePersistImplicit
}

/** Trait for Show for product of 2 Doubles. This trait is implemented directly by the type in question, unlike the corresponding [[ShowShowDbl2T]]
 *  trait which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred
 *  over [[Show2T]]. */
trait ShowElemDbl3 extends Any with ShowDbl3 with ElemDbl3
{ final override def dbl1: Double = show1
  final override def dbl2: Double = show2
  final override def dbl3: Double = show2
}

/** Show type class for 3 parameter case classes. */
trait Show3T[A1, A2, A3, R] extends ShowProductT[R] with ShowSelf3[A1, A2, A3]
{ def fArg1: R => A1
  def fArg2: R => A2
  def fArg3: R => A3
  def ev1: ShowT[A1]
  def ev2: ShowT[A2]
  def ev3: ShowT[A3]
  override def syntaxDepthT(obj: R): Int = ev1.syntaxDepthT(fArg1(obj)).max(ev2.syntaxDepthT(fArg2(obj))).max(ev3.syntaxDepthT(fArg3(obj))) + 1

  override def strs(obj: R, way: ShowStyle, decimalPlaces: Int): Strings =
    Strings(ev1.showT(fArg1(obj), way, decimalPlaces, 0), ev2.showT(fArg2(obj), way, decimalPlaces, 0), ev3.showT(fArg3(obj), way, decimalPlaces, 0))
}

object Show3T
{
  def apply[A1, A2, A3, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    opt3: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(implicit ev1: ShowT[A1], ev2: ShowT[A2], ev3: ShowT[A3]):
    Show3T[A1, A2, A3, R] = new Show3TImp[A1, A2, A3, R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3,opt3, opt2In, opt1In)

  class Show3TImp[A1, A2, A3, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2, val name3: String,
    val fArg3: R => A3, val opt3: Option[A3] = None, opt2In: Option[A2] = None, opt1In: Option[A1] = None)(
    implicit val ev1: ShowT[A1], val ev2: ShowT[A2], val ev3: ShowT[A3]) extends Show3T[A1, A2, A3, R]
  {
    val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
    val defaultNum = ife3(opt3.isEmpty, 0, opt2.isEmpty, 1, opt1.isEmpty, 2, 3)
  }
}

trait ShowShow3T[A1, A2, A3, R <: Show3[A1, A2, A3]] extends Show3T[A1, A2, A3, R] with ShowShowT[R]
{ override def fArg1: R => A1 = _.show1
  override def fArg2: R => A2 = _.show2
  override def fArg3: R => A3 = _.show3
}

trait ShowShowDbl3T[R <: ShowDbl3] extends ShowShow3T[Double, Double, Double, R]
{ override implicit def ev1: Persist[Double] = ShowT.doublePersistImplicit
  override implicit def ev2: Persist[Double] = ShowT.doublePersistImplicit
  override implicit def ev3: Persist[Double] = ShowT.doublePersistImplicit
}

/*
case class Show3DblsT[T](typeStr: String) extends ShowT[T]{
  /** Provides the standard string representation for the object. Its called ShowT to indicate this is a type class method that acts upon an object
   * rather than a method on the object being shown. */
  override def strT(obj: T): String = ???

  def elemStrs(obj: T, way: Show.Way, maxPlaces: Int, minPlaces: Int)
  override def showT(obj: T, way: Show.Way, maxPlaces: Int, minPlaces: Int): String = way match {
    case Show.Semis =>
  }

  /** Simple values such as Int, String, Double have a syntax depth of one. A Tuple3[String, Int, Double] has a depth of 2. Not clear whether this
   * should always be determined at compile time or if sometimes it should be determined at runtime. */
  override def syntaxDepthT(obj: T): Int = 1

  /** The RSON type of T. This the only data that a ShowT instance requires, that can't be implemented through delegation to an object of type
   * Show. */

}*/

/** UnShow class for 3 logical parameter product types. */
trait UnShow3[A1, A2, A3, R] extends UnShowProduct[R] with ShowSelf3[A1, A2, A3]
{
  /** The UnShow type class instance for type A1. */
  def ev1: UnShow[A1]

  /** The UnShow type class instance for type A2. */
  def ev2: UnShow[A2]

  /** The UnShow type class instance for type A2. */
  def ev3: UnShow[A3]

  def newT: (A1, A2, A3) => R
}

object UnShow3
{
  /*(val typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
  val newT: (A1, A2, A3) => R, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: UnShow[A1], ev2: UnShow[A2],
  ev3: UnShow[A3], eq1: EqT[A1], eq2: EqT[A2], eq3: EqT[A3])*/
}

/** Persistence class for 3 logical parameter product types. */
trait Persist3[A1, A2, A3, R] extends Show3T[A1, A2, A3, R] with UnShow3[A1, A2, A3, R] with PersistProduct[R]
{ override def ev1: Persist[A1]
  override def ev2: Persist[A2]
  override def ev3: Persist[A3]
}

/** Companion object for [[Persist3]] trait contains implementation class and factory apply method. */
object Persist3
{
  def apply[A1, A2, A3, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, name3: String, fArg3: R => A3,
    newT: (A1, A2, A3) => R, opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(
    implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], eq1: EqT[A1], eq2: EqT[A2], eq3: EqT[A3]): Persist3[A1, A2, A3, R] =
    new Persist3Imp(typeStr, name1, fArg1, name2, fArg2, name3, fArg3, newT, opt3, opt2, opt1)(ev1, ev2, ev3)

  class Persist3Imp[A1, A2, A3, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2,
    val name3: String, val fArg3: R => A3, val newT: (A1, A2, A3) => R, val opt3: Option[A3] = None, opt2In: Option[A2] = None,
    opt1In: Option[A1] = None)(implicit val ev1: Persist[A1], val ev2: Persist[A2], val ev3: Persist[A3]) extends Persist3[A1, A2, A3, R]
  { val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
    val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
    val defaultNum = ife3(opt3.isEmpty, 0, opt2.isEmpty, 1, opt1.isEmpty, 2, 3)
  }
}

/** Persistence class for case classes consisting of 3 Double parameters. */
/*
abstract class PersistD3[R](typeStr: String, name1: String, fArg1: R => Double, name2: String, fArg2: R => Double, name3: String, fArg3: R => Double,
  newT: (Double, Double, Double) => R) extends Persist3[Double, Double, Double, R](typeStr, name1, fArg1, name2, fArg2, name3, fArg3, newT)*/
/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import reflect.ClassTag

/** [[Tell]] trait for classes with 3+ Show parameters. */
trait Tell3Plused[A1, A2, A3] extends Any with Tell2Plused[A1, A2] with Persist3Plus[A1, A2, A3]
{ /** Element 3 of this Show 3+ element product. */
  def tell3: A3

  def show3: Show[A3]

  /** The optional default value for parameter 3. */
  override def opt3: Option[A3] = None
}

/** Trait for [[TellDec]] for a product of 3 logical elements. This trait is implemented directly by the type in question, unlike the corresponding
 *  [[ShowEq3T]] trait which externally acts on an object of the specified type to create its String representations. For your own types it is better to
 *  inherit from Show3 and then use [[Show3ElemT]] or [[Persist3ElemT]] to create the type class instance for ShowT. The [[Show3ElemT]] or
 *  [[Persist3Elem]] class will delegate to Show3 for some of its methods. It is better to use Show3 to override toString method than delegating the
 *  toString override to a [[ShowEq3T]] instance. */
trait Tell3[A1, A2, A3] extends Any with Tell3Plused[A1, A2, A3]
{ override def numParams: Int = 3
  override def paramNames: StrArr = StrArr(name1, name2, name3)
  override def elemTypeNames: StrArr = StrArr(show1.typeStr, show2.typeStr, show3.typeStr)

  override def tellElemStrs(way: ShowStyle, decimalPlaces: Int = -1, minPlaces: Int = 0): StrArr =
  {
    def s1 = show1.show(tell1, way, decimalPlaces, minPlaces)
    def s2 = show2.show(tell2, way, decimalPlaces, minPlaces)
    def s3 = show3.show(tell3, way, decimalPlaces, minPlaces)
    opt3 match
    { case Some(v3) if v3 == tell3 && opt2 == Some(tell2) && opt1 == Some(tell1) => StrArr()
      case Some(v3) if v3 == tell3 && opt2 == Some(tell2) => StrArr(s1)
      case Some(v3) if v3 == tell3 => StrArr(s1, s2)
      case _ => StrArr(s1, s2, s3)
    }
  }
}

/** Show classes with 3 [[Int]] parameters. */
trait TellInt3 extends Any with Tell3[Int, Int, Int]
{ final override def tellDepth: Int = 2
  final override implicit def show1: Show[Int] = Show.intEv
  final override implicit def show2: Show[Int] = Show.intEv
  final override implicit def show3: Show[Int] = Show.intEv
}

/** Show classes with 3 [[Double]] parameters. */
trait TellDbl3 extends Any with Tell3[Double, Double, Double]
{ final override def tellDepth: Int = 2
  final override implicit def show1: Show[Double] = Show.doubleEv
  final override implicit def show2: Show[Double] = Show.doubleEv
  final override implicit def show3: Show[Double] = Show.doubleEv
}

/** Type class trait for Showing [[Tell3]] objects. */
trait ShowTell3[A1, A2, A3, R <: Tell3[A1, A2, A3]] extends ShowTell[R]

object ShowTell3
{
  def apply[A1, A2, A3, R <: Tell3[A1, A2, A3]](typeStr: String)(implicit ev1: Show[A1], ev2: Show[A2], ev3: Show[A3]): ShowTell3[A1, A2, A3, R] =
    new ShowTell3Imp[A1, A2, A3, R](typeStr)

  /** Implementation class for the general cases of the [[ShowTell3]] trait. */
  class ShowTell3Imp[A1, A2, A3, R<: Tell3[A1, A2, A3]](val typeStr: String)(implicit val show1: Show[A1], val show2: Show[A2], val show3: Show[A3]) extends
    ShowTell3[A1, A2, A3, R]
}

/** Class to provide both [[Show]] and [[Unshow]] type class instances for [[Tell3]] objects. */
class PersistTell3[A1, A2, A3, A <: Tell3[A1, A2, A3]](val typeStr: String, val name1: String, val name2: String, val name3: String,
  val shortKeys: ArrPairStr[A], val newT: (A1, A2, A3) => A, override val opt3: Option[A3], opt2In: Option[A2], opt1In: Option[A1])(implicit
  val unshow1Ev: Unshow[A1], val unshow2Ev: Unshow[A2], val unshow3Ev: Unshow[A3]) extends PersistTell[A] with ShowTell3[A1, A2, A3, A] with
  Unshow3[A1, A2, A3, A]
{ override val opt2: Option[A2] = ife(opt3.nonEmpty, opt2In, None)
  override val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
}

object PersistTell3
{ /** Factory apply method for creating [[PersistTell3]] type type class instances / evidence. */
  def apply[A1, A2, A3, A <: Tell3[A1, A2, A3]](typeStr: String, name1: String, name2: String, name3: String, newT: (A1, A2, A3) => A, opt3: Option[A3] = None,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit unshow1Ev: Unshow[A1], unshow2Ev: Unshow[A2], unshow3Ev: Unshow[A3], classTag: ClassTag[A]):
    PersistTell3[A1, A2, A3, A] = new PersistTell3[A1, A2, A3, A](typeStr, name1, name2, name3, ArrPairStr[A](), newT, opt3, opt2, opt1)

  /** Factory method for creating [[PersistTell3]] type type class instances / evidence with short labels. */
  def shorts[A1, A2, A3, A <: Tell3[A1, A2, A3]](typeStr: String, name1: String, name2: String, name3: String, shorts: ArrPairStr[A], newT: (A1, A2, A3) => A,
    opt3: Option[A3] = None, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit unshow1Ev: Unshow[A1], unshow2Ev: Unshow[A2], unshow3Ev: Unshow[A3],
    classTag: ClassTag[A]): PersistTell3[A1, A2, A3, A] = new PersistTell3[A1, A2, A3, A](typeStr, name1, name2, name3, shorts, newT, opt3, opt2, opt1)

  /** Factory method for creating [[PersistTell3]] type class instances / evidence, by explicitly passing the [[Unshow]] type class instances for the two
   * components. */
  def explicit[A1, A2, A3, A <: Tell3[A1, A2, A3]](typeStr: String, name1: String, name2: String, name3: String, newT: (A1, A2, A3) => A, unshow1Ev: Unshow[A1],
    unshow2Ev: Unshow[A2], unshow3Ev: Unshow[A3], opt3: Option[A3] = None,  opt2: Option[A2] = None,opt1: Option[A1] = None)(implicit ct: ClassTag[A]):
    PersistTell3[A1, A2, A3, A] =
    new PersistTell3[A1, A2, A3, A](typeStr, name1, name2, name3, ArrPairStr[A](), newT, opt3, opt2, opt1)(unshow1Ev, unshow2Ev, unshow3Ev)
}
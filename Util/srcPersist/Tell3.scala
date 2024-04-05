/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

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

/** Type class trait for Showing [[Tell2]] objects. */
trait ShowTell3[A1, A2, A3, R <: Tell3[A1, A2, A3]] extends ShowTell[R]

object ShowTell3
{
  def apply[A1, A2, A3, R <: Tell3[A1, A2, A3]](typeStr: String)(implicit ev1: Show[A1], ev2: Show[A2], ev3: Show[A3]): ShowTell3[A1, A2, A3, R] =
    new ShowTell3Imp[A1, A2, A3, R](typeStr)

  /** Implementation class for the general cases of the [[ShowTell2]] trait. */
  class ShowTell3Imp[A1, A2, A3, R<: Tell3[A1, A2, A3]](val typeStr: String)(implicit val show1: Show[A1], val show2: Show[A2], val show3: Show[A3]) extends
    ShowTell3[A1, A2, A3, R]
}
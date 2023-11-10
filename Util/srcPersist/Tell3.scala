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

  override def showElemStrs(way: ShowStyle): StrArr = StrArr(show1.show(tell1, way), show2.show(tell2, way), show3.show(tell3, way))

  override def showElemStrDecs(way: ShowStyle, decimalPlaces: Int): StrArr =
    StrArr(show1.show(tell1, way, decimalPlaces, 0), show2.show(tell2, way, decimalPlaces, 0),
      show3.show(tell3, way, decimalPlaces, 0))
}

/** Show classes with 3 [[Int]] parameters. */
trait TellInt3 extends Any with Tell3[Int, Int, Int]
{ final override def syntaxDepth: Int = 2
  final override implicit def show1: Show[Int] = Show.intEv
  final override implicit def show2: Show[Int] = Show.intEv
  final override implicit def show3: Show[Int] = Show.intEv
}

/** Show classes with 3 [[Double]] parameters. */
trait TellDbl3 extends Any with Tell3[Double, Double, Double]
{ final override def syntaxDepth: Int = 2
  final override implicit def show1: Show[Double] = Show.doublePersistEv
  final override implicit def show2: Show[Double] = Show.doublePersistEv
  final override implicit def show3: Show[Double] = Show.doublePersistEv
}

/** [[Show]] type class for [[Tell3]] types. */
class ShowTell3[A1, A2, A3, R <: Tell3[A1, A2, A3]](val typeStr: String) extends ShowTell[R]

object ShowTell3
{ /** Factory apply method for creating [[ShowTell3]] instances. */
  def apply[A1, A2, A3, R <: Tell3[A1, A2, A3]](typeStr: String): ShowTell3[A1, A2, A3, R] = new ShowTell3[A1, A2, A3, R](typeStr)
}

/** [[Show]] type class for [[TellInt3]] types. */
class ShowTellInt3[R <: TellInt3](typeStr: String) extends ShowTell3[Int, Int, Int, R](typeStr)

object ShowTellInt3
{ /** Factory apply method for creating [[ShowTell3]] instances. */
  def apply[R <: TellInt3](typeStr: String): ShowTellInt3[R] = new ShowTellInt3[R](typeStr)
}
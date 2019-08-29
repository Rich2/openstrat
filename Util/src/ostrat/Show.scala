/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

/** The classic Show type class. A functional version of toString .Mostly you will want to use Persist which not only gives the Show methods
 *   to String representation, but the methods to parse Strings back to objects of the type T. However it may often be useful to start with Show
 *   type class and upgrade it later to Persist[T]. */
trait Show[-T]
{
  def typeStr: String
  /** Provides the standard string representation for the object. */
  def show(obj: T): String
  
  /** Simple values such as Int, String, Double have a syntax depth of one. A Tuple3[String, Int, Double] has a depth of 2 */
  def syntaxDepth: Int  
  
  /** Return the defining member values of the type as a series of comma separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 3. if it is 3 or greater return the full typed data. */
  def showComma(obj: T): String
  
  /** Return the defining member values of the type as a series of semicolon separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 4. if it is 4 or greater return the full typed data. This method is not commonly needed but is useful
   *  for case classes with a single member. This method will rarely be used, as it is only applicable when the object is being shown stand alone and
   *  not as part of a containing object. So generally the full show method string will be desired. It may have uses for on the fly aggregation of
   *  strings. */
  def showSemi(obj: T): String
  
  /** For most objects showTyped will return the same value as show(obj: T), for PeristValues the value will be type enclosed. 4.showTyped
   * will return Int(4) */
  def showTyped(obj: T): String
}

object Show
{
  implicit val IntImplicit: Show[Int] = new ShowSimple[Int]("Int")
  { def show(obj: Int): String = obj.toString
  }

  implicit val DoubleImplicit: Show[Double] = new ShowSimple[Double]("DFloat")
  { def show(obj: Double): String = obj.toString
  }

  implicit val BooleanImplicit: Show[Boolean] = new ShowSimple[Boolean]("Bool")
  { override def show(obj: Boolean): String = obj.toString
  }

  implicit val ArrIntImplicit: Show[Arr[Int]] = new ShowSeqLike[Int, Arr[Int]]
  {
    override val evA: Show[Int] = Show.IntImplicit
    override def showSemi(thisArray: Arr[Int]): String = thisArray.map(evA.showComma(_)).semiFold
    override def showComma(thisArray: Arr[Int]): String = thisArray.map(evA.show(_)).commaFold
  }

  implicit val ArrayIntImplicit: Show[Array[Int]] = new ShowSeqLike[Int, Array[Int]]
  { override val evA: Show[Int] = Show.IntImplicit
    override def showSemi(thisArray: Array[Int]): String = thisArray.map(evA.showComma(_)).semiFold
    override def showComma(thisArray: Array[Int]): String = thisArray.map(evA.show(_)).commaFold
  }
}
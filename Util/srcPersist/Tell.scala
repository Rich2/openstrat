/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A trait that is showed by itself. for providing an alternative to toString. Using this trait can be convenient, but at some level of the
 *  inheritance the type must provide a [[Show]] type class instance. It is better for the [[Show]] type class instance to delegate to this trait
 *  than have the toString method delegate to the [[Show]] type class instance in the companion object. Potentially that can create initialisation
 *  order problems, but at the very least it can increase compile times. The capabilities of decimal place precision and explicit typing for numbers
 *  are placed defined here and in the corresponding [[Show]] type class although they have no meaning / purpose for many types, as separating them
 *  adds enormous complexity for very little gain. */
trait Tell extends Any with PersistBase
{
  /** The most basic Show method, paralleling the strT method on ShowT type class instances. */
  def str: String

  def syntaxDepth: Int

  override def toString: String = str

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Show]] type class instances. */
  def tell(style: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = -1): String
}

/** [[Tell]] type that does not use [[Double]]s and [[Float]]s where precision may need to be specified. */
trait TellQuanta extends Any with Tell
{ override def tell(style: ShowStyle, maxPlaces: Int, minPlaces: Int): String = tell(style)
}

/** [[Tell]] decimal. A trait which can be displayed /persisted with varying levels of decimal precison. */
trait TellDec extends Any with Tell
{
  def str: String = tell(ShowStandard, -1, 0)

  /** Show with decimal precision of 0 places. */
  def str0: String = tell(ShowStandard, 0, 0)

  /** Show with decimal precision of 1 place padding with a zero if necessary. */
  def str1: String = tell(ShowStandard, 1, 1)

  /** Show with decimal precision of 2 places padding with zeros if necessary. */
  def str2: String = tell(ShowStandard, 2, 2)

  /** Show with decimal precision of 3 places padding with zeros if necessary. */
  def str3: String = tell(ShowStandard, 3, 3)
}


/** A sub trait of the [[Show]] sub class where the type parameter of ShowT extends Show. This allows the ShowT type class to delegate to the Show
 * class for the implementation of its strT and ShowT methods. It is better to use [[TellDec]] and ShowElemT for types you control than have the toString
 * method delegate to the [[Show]] type class instance in the companion object. Potentially that can create initialisation order problems, but at the
 * very least it can increase compile times. */
trait ShowTell[R <: Tell] extends Show[R]
{ override def strT(obj: R): String = obj.str
  override def syntaxDepth(obj: R): Int = obj.syntaxDepth
  override def show(obj: R, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String = obj.tell(way, maxPlaces, minPlaces)
}

object ShowTell
{
  def apply[R <: Tell](typeStrIn: String): ShowTell[R] = new ShowTell[R]
  { override def typeStr: String = typeStrIn
  }
}

trait ShowTellSum[R <: Tell] extends Show[R]
{ override def strT(obj: R): String = obj.str
  override def syntaxDepth(obj: R): Int = obj.syntaxDepth
  override def show(obj: R, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String = obj.tell(way.full, maxPlaces, minPlaces)
}

object ShowTellSum
{
  def apply[R <: Tell](typeStrIn: String): ShowTellSum[R] = new ShowTellSum[R]
  { override def typeStr: String = typeStrIn
  }
}
/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A trait that is showed by itself. for providing an alternative to toString. Using this trait can be convenient, but at some level of the
 *  inheritance the type must provide a [[Show]] type class instance. It is better for the [[Show]] type class instance to delegate to this trait
 *  than have the toString method delegate to the [[Show]] type class instance in the companion object. Potentially that can create initialisation
 *  order problems, but at the very least it can increase compile times. The capabilities of decimal place precision and explicit typing for numbers
 *  are placed defined here and in the corresponding [[Show]] type class although they have no meaning / purpose for many types, as separating them
 *  adds enormous complexity for very little gain. */
trait Tell extends Any with Persist
{
  /** The most basic Show method, paralleling the strT method on ShowT type class instances. */
  def str: String

  /** The syntactic depth of the tell [[String]] for this object. */
  def tellDepth: Int

  override def toString: String = str

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by show method on [[Show]] type class instances. */
  def tell(style: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String

  /** Show with decimal precision of 0 places. */
  def str0: String = tell(ShowStdNoSpace, 0, 0)

  /** Show with decimal precision of 1 place padding with a zero if necessary. */
  def str1: String = tell(ShowStdNoSpace, 1, 1)

  /** Show with decimal precision of 2 places padding with zeros if necessary. */
  def str2: String = tell(ShowStdNoSpace, 2, 2)

  /** Show with decimal precision of 3 places padding with zeros if necessary. */
  def str3: String = tell(ShowStdNoSpace, 3, 3)
}

trait TellDblBased extends Any with Tell
{
  def endingStr: String

  def unitsDbl: Double

  override def str: String = tell(ShowStd, -1, -1)

  override def tellDepth: Int = 1

  override def tell(style: ShowStyle, maxPlaces: Int, minPlaces: Int): String = style match {
    case st => Show.doubleEv.show(unitsDbl, ShowStd, maxPlaces, minPlaces) + endingStr
  }

  override def toString: String = tell(ShowStd, -1, -1)
}

/** A sub trait of the [[Show]] sub class where the type parameter extends [[Tell]]. This allows this [[Show]] type class to delegate to the [[Tell]]
 * object for the implementation of its strT and Show methods. It is better to use [[Tell]] and [[ShowTell]] for types you control rather than have
 * the toString method delegate to the [[Show]] type class instance in the companion object. Potentially that can create initialisation order
 * problems, but at the very least it can increase compile times. The typeStr is the only data that a [[Show]] instance requires, that can't be
 * implemented through delegation to the [[Tell]] object. */
trait ShowTell[A <: Tell] extends Show[A]
{ override def strT(obj: A): String = obj.str
  override def syntaxDepth(obj: A): Int = obj.tellDepth
  override def show(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String = obj.tell(way, maxPlaces, minPlaces)
}

object ShowTell
{ /** Factory apply method for [[ShowTell]] type class instances / evidence. */
  def apply[A <: Tell](typeStrIn: String): ShowTell[A] = new ShowTell[A]
  { override def typeStr: String = typeStrIn
  }
}

trait ShowTellSum[A <: Tell] extends Show[A]
{ override def strT(obj: A): String = obj.str
  override def syntaxDepth(obj: A): Int = obj.tellDepth
  override def show(obj: A, way: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String = obj.tell(way.full, maxPlaces, minPlaces)
}

object ShowTellSum
{
  def apply[A <: Tell](typeStrIn: String): ShowTellSum[A] = new ShowTellSum[A]
  { override def typeStr: String = typeStrIn
  }
}

/** Type class inatances for both [[Show]] and [[Unshow]]. Only use this class where all possilbe requirements have PersistBoth instances. Do not use
 * it for such types as Sequences where all the potential components are not known. */
trait PersistTell[A <: Tell] extends ShowTell[A] with Unshow[A]
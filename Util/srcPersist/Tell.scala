/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.immutable.ArraySeq

/** A trait that is Showed by itself. for providing an alternative to toString. Using this trait can be convenient, but at some level of the
 *  inheritance the type must provide a [[Showing]] type class instance. It is better for the [[Showing]] type class instance to delegate to this trait
 *  than have the toString method delegate to the [[Showing]] type class instance in the companion object. Potentially that can create initialisation
 *  order problems, but at the very least it can increase compile times. The capabilities of decimal place precision and explicit typing for numbers
 *  are placed defined here and in the corresponding [[SHowT]] type class although they have no meaning / purpose for many types, as separating them
 *  adds enormous complexity for very little gain. */
trait Tell extends Any with PersistBase
{
  /** The most basic Show method, paralleling the strT method on ShowT type class instances. */
  def str: String

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Showing]] type class instances. */
  def tell(style: ShowStyle = ShowStandard): String

  def syntaxDepth: Int

  override def toString: String = str

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Showing]] type class instances. */
  def showDec(style: ShowStyle = ShowStandard, maxPlaces: Int): String = showDec(style, maxPlaces, maxPlaces)

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Showing]] type class instances. */
  def showDec(style: ShowStyle, maxPlaces: Int, minPlaces: Int): String

  //def strSemi: String = show(ShowSemis)
}

/** [[Tell]] type that does not use [[Double]]s and [[Float]]s where precision may need to be specified. */
trait ShowQuantaed extends Any with Tell
{ override def showDec(style: ShowStyle, maxPlaces: Int, minPlaces: Int): String = tell(style)
}

/** [[Tell]] decimal. A trait which can be displayed /persisted with varying levels of decimal precison. */
trait TellDec extends Any with Tell
{
  override def tell(style: ShowStyle = ShowStandard): String = showDec(style, -1, -1)

  def str: String = showDec(ShowStandard, -1, 0)

  /** Show with decimal precision of 0 places. */
  def str0: String = showDec(ShowStandard, 0, 0)

  /** Show with decimal precision of 1 place padding with a zero if necessary. */
  def str1: String = showDec(ShowStandard, 1, 1)

  /** Show with decimal precision of 2 places padding with zeros if necessary. */
  def str2: String = showDec(ShowStandard, 2, 2)

  /** Show with decimal precision of 3 places padding with zeros if necessary. */
  def str3: String = showDec(ShowStandard, 3, 3)
}
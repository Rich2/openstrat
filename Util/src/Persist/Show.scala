/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Common super trait for [[ShowDec]], [[ShowDecT]] and [[Unshow]]. All of which inherit the typeStr property. */
trait TypeStr extends Any
{ /** The RSON type of T. This the only data that a ShowT instance requires, that can't be implemented through delegation to an object of type
    * Show. */
  def typeStr: String
}

trait Show extends Any with TypeStr
{ /** The most basic Show method, paralleling the strT method on ShowT type class instances. */
  def str: String

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowDecT]] type class instances. */
  def show(style: ShowStyle = ShowStandard): String

  def syntaxDepth: Int

  override def toString: String = str
}

/** A trait for providing an alternative to toString. USing this trait can be convenient, but at some level of the inheritance the type must provide a
 *  ShowT type class instance. It is better for the [[ShowDecT]] type class instance to delegate to this trait than have the toString method delegate to
 *  the [[ShowDecT]] type class instance in the companion object. Potentially that can create initialisation order problems, but at the very least it
 *  can increase compile times. The capabilities of decimal place precision and explicit typing for numbers are placed defined here and in the
 *  corresponding [[SHowT]] type class although they have n meaning / purpose for many types, as seperating them adds enormous complexity for very
 *  little gain. */
trait ShowDec extends Any with Show
{
  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowDecT]] type class instances. */
  def showDec(style: ShowStyle = ShowStandard, maxPlaces: Int): String = showDec(style, maxPlaces, maxPlaces)

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowDecT]] type class instances. */
  def showDec(style: ShowStyle, maxPlaces: Int, minPlaces: Int): String

  override def show(style: ShowStyle = ShowStandard): String = showDec(style, -1, -1)

  override def str: String = showDec(ShowStandard, -1, 0)

  /** Show with decimal precision of 0 places. */
  def str0: String = showDec(ShowStandard, 0, 0)

  /** Show with decimal precision of 1 place padding with a zero if necessary. */
  def str1: String = showDec(ShowStandard, 1, 1)

  /** Show with decimal precision of 2 places padding with zeros if necessary. */
  def str2: String = showDec(ShowStandard, 2, 2)

  /** Show with decimal precision of 3 places padding with zeros if necessary. */
  def str3: String = showDec(ShowStandard, 3, 3)
}

/** Currently can't think of a better name for this trait */
sealed trait ShowStyle

/** Show the object just as its comma separated constituent values. */
object ShowCommas extends ShowStyle

/** Show the object as semicolon separated constituent values. */
object ShowSemis extends ShowStyle

/** Show the object in the standard default manner. */
object ShowStandard extends ShowStyle

/** Show the object in the standard default manner, with parameter names. */
object ShowParamNames extends ShowStyle

/** Show the object as semicolon separated constituent values preceded b y their parameter names. */
object ShowSemisNames extends ShowStyle

/** Show the object in the standard default manner, with field names and their types. */
object ShowStdTypedFields extends ShowStyle

/** Show the object with the type of the object even if the string representation does not normally states its type. Eg Int(7). */
object ShowTyped extends ShowStyle

/** Represents the object with an underscore. */
object ShowUnderScore extends ShowStyle
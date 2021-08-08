/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A trait for providing an alternative to toString. USing this trait can be convenient, but at some level of the inheritance the type must provide a
 *  ShowT type class instance. It is better for the [[ShowT]] type class instance to delegate to this trait than have the toString method delegate to
 *  the [[ShowT]] type class instance in the companion object. Potentially that can create initialisation order problems, but at the very least it
 *  can increase compile times. */
trait Show extends Any
{ /** the name of the type of this object. */
  def typeStr: String

  /** The most basic Show method, paralleling the strT method on ShowT type class instances. */
  def str: String

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowT]] type class instances. */
  def show(way: Show.Way = Show.Standard, maxPlaces: Int = -1, minPlaces: Int = 0): String

  def syntaxdepth: Int

  override def toString: String = str
}

/** Companion object of the Show trait contains the Way trait, used by the show method on Show and the showT method on [[ShowT]] */
object Show
{ /** Currently can't think of a better name for this trait */
  sealed trait Way

  /** Show the object just as its comma separated constituent values. */
  object Commas extends Way

  /** Show the object as semicolon separated constituent values. */
  object Semis extends Way

  /** Show the object in the standard default manner. */
  object Standard extends Way

  /** Show the object in the standard default manner, with field names. */
  object StdFields extends Way

  /** Show the object in the standard default manner, with field names and their types. */
  object StdTypedFields extends Way

  /** Show the object with the type of the object even if the string representation does not normally states its type. Eg Int(7). */
  object Typed extends Way

  /** Represents the object with an underscore. */
  object UnderScore extends Way
}

/** All the leaves of this trait must be Singleton objects. They just need to implement the str method. This will normally be the name of the object,
 *  but sometimes, it may be a lengthened or shortened version of the singleton object name. */
trait ShowSingleton extends Show
{ /** The string for the leaf object. This will normally be different from the typeStr in the instance of the PersistSingletons. */
  def str: String

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowT]] type class instances. */
  final override def show(way: Show.Way, maxPlaces: Int, minPlaces: Int): String = way match
  { case Show.Typed => typeStr.appendParenth(str)
    case Show.UnderScore => "_"
    case _ => str
  }

  override def syntaxdepth: Int = 1
}

/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A trait for providing an alternative to toString. USing this trait can be conveient, but at some level the type must provide a ShowT type class
 *  instance. */
trait Show extends Any
{ def typeStr: String

  /** The most basic Show method, paralleling the strT method on ShowT type class instances. */
  def str: String

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowT]] type class instances. */
  def show(decimalPlaces: Int): String = str
}

/** All the leafs of this trait must be Singleton objects. They just need to implement the str method. This will normally be the name of the object,
 *  but sometimes, it may be a lengthened or shortened version of the singleton object name. */
trait ShowSingleton extends Show
{ /** The string for the leaf object. This will normally be different from the typeStr in the instance of the PersistSingletons. */
  def str: String
  override def toString: String = str
}
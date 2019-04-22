/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** mostly you will want to use Persist which not only igves the Show methods to String represntation, but the methods to parse Strings back to 
 *  objects of the type T. However it may often be useful to start with Show and upgrade it later to Persist[T]. */
trait Show[T]
{
  /** Provides the standard string representation for the object */
  def show(obj: T): String
}

abstract class ShowSingleton[T](objSym: Symbol)
{
  def str: String = objSym.name
  override def toString: String = objSym.name
}


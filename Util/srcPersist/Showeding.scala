/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A sub trait of the [[Showing]] sub class where the type parameter of ShowT extends Show. This allows the ShowT type class to delegate to the Show
 * class for the implementation of its strT and ShowT methods. It is better to use [[ShowDec]] and ShowElemT for types you control than have the toString
 * method delegate to the [[Showing]] type class instance in the companion object. Potentially that can create initialisation order problems, but at the
 * very least it can increase compile times. */
trait Showeding[R <: Showed] extends Showing[R]
{ override def strT(obj: R): String = obj.str
  override def showT(obj: R, way: ShowStyle): String = obj.show(way)
  override def syntaxDepthT(obj: R): Int = obj.syntaxDepth
  override def showDecT(obj: R, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = obj.showDec(way, maxPlaces, 0)
}

object Showeding
{
  def apply[R <: Showed](typeStrIn: String): Showeding[R] = new Showeding[R]
  { override def typeStr: String = typeStrIn
  }
}

/** ShowT instances for [[ShowSimpled]] types. */
case class ShowShowSimpleT[R <: ShowSimpled](typeStr: String) extends Showeding[R]
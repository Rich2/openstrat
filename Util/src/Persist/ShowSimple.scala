/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Shows a simple object like a Singleton object or a Double. For your own objects that you control it is better to use Show and its helper sub
 * rather than the sub traits of ShowT to implement your Show functionality.S */
trait ShowSimpleT[-A] extends ShowT[A]
{
  final override def syntaxDepthT(obj: A): Int = 1

  override def showT(obj: A, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = way match {
    case ShowTyped => typeStr + strT(obj).enParenth
    case _ => strT(obj)
  }
}

/** A Persist class described by a single value. This may be removed. Its not clear whether this means a single token or not. */
abstract class PersistSimple[A](val typeStr: String) extends ShowSimpleT[A] with Persist[A]
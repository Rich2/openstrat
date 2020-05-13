/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

trait ReflectAxis[T]
{ /** Reflect, mirror across the X axis. */
  def reflectX: T

  /** Reflect, mirror across the Y axis. */
  def reflectY: T
}

object ReflectAxis
{

}
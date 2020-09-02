/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

trait ReflectAxis[T]
{ /** Reflect, mirror an object of type T across the X axis. */
  def reflectXT(obj: T): T

  /** Reflect, mirror an object of type T across the Y axis. */
  def reflectYT(obj: T): T
}



class ReflectAxisExtension[A](thisReflector: A)(implicit ev: ReflectAxis[A])
{
  
}

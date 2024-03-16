/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
/*import zio._

package object pDev
{ def packStr: String = "This is pDev"
  object NoneExc extends RuntimeException
  type IOTask[A] = ZIO[Any, java.io.IOException | NoneExc.type, A]
  type ZOpt[A] = ZIO[Any, NoneExc.type, A]

  implicit class OptionDevExtensions[A](thisOption: Option[A])
  { def toZio: ZOpt[A] = thisOption match
    { case Some(a) => ZIO.succeed(a)
      case None => ZIO.fail(NoneExc)
    }
  }
}*/
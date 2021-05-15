/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait  ShowPT[-T] //extends ShowT[T]
{
  def showPT(obj: T, way: Show.Way, maxPlaces: Int, minPlaces: Int): String
}

object ShowPT
{

  /*override def showPT(obj: Double, way: Show.Way, maxPlaces: Int, minPlaces: Int): String =
  {
    val s1 = obj.toString
    val len = s1.length
    val i = s1.indexOf('.')

    val inner = i match {
      case i if maxPlaces < 0 => s1
      case i if maxPlaces == 0 => s1.dropRight(len  - i - maxPlaces)
      case i if len > maxPlaces + i + 1 => s1.dropRight(len  - i - 1 - maxPlaces)
      case i if len - i - 1 < minPlaces => s1 + (minPlaces + i + i - len).repeatChar('0')
      case _ => s1
    }

    way match {
      case Show.Typed => typeStr + inner.enParenth
      case _ => inner
    }
  }*/
}

trait PersistP[T] extends Persist[T] with ShowPT[T]
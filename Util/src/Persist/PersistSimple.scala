/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A Persist class described by a single value. This may be removed. Its not clear whether this means a single token or not. */
abstract class PersistSimple[A](typeStr: String) extends ShowSimpleT[A](typeStr) with Persist[A]

abstract class ShowSimpleT[-A](val typeStr: String) extends ShowT[A]
{
  final override def syntaxDepthT(obj: A): Int = 1

  override def showT(obj: A, way: Show.Way, decimalPlaces: Int): String = way match {
    case Show.Typed => typeStr + strT(obj).enParenth
    case _ => strT(obj)
  }

  override def showComma(obj: A): String = strT(obj)
  override def showSemi(obj: A): String = strT(obj)
}

abstract class PersistPrecisionSimple[A](typeStr: String) extends ShowPrecisionSimpleT[A](typeStr) with Persist[A]

abstract class ShowPrecisionSimpleT[-A](val typeStr: String) extends ShowT[A]
{
  final override def syntaxDepthT(obj: A): Int = 1

  override def showComma(obj: A): String = strT(obj)
  override def showSemi(obj: A): String = strT(obj)
}
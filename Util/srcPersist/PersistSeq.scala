/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait PersistSeq[A, SA] extends Persist
{ /** All Sequences are persisted with the "Seq" type. */
  override def typeStr: String = "Seq"

  override def useMultiple: Boolean = false
}

/** All logical sequence classes are shown as "Seq"s. There encoding in memory and the immutability are irrelevant for their persistence. */
trait ShowSeq[A, SA] extends ShowSeqLike[A, SA] with PersistSeq[A, SA]
{
  override def toString: String = "Show" + typeStr + evA.typeStr.enSquare
}

trait ShowIterable[A, SA <: Iterable[A]] extends ShowSeq[A, SA]
{ override def showForeach(obj: SA, f: A => Unit): Unit = obj.foreach(f)
}

/** [[Show] type class for showing [[Sequ]][A] objects. */
trait ShowSequ[A, SA <: Sequ[A]] extends ShowSeq[A, SA]
{ override def showForeach(obj: SA, f: A => Unit): Unit = obj.foreach(a => f(a))
}

object ShowSequ
{
  def apply[A, SA <: Sequ[A]]()(implicit evAIn: Show[A]): ShowSequ[A, SA] = new ShowSequ[A, SA]
  { override val evA: Show[A] = evAIn
  }
}

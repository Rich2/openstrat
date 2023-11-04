/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait PersistBaseSeq[A, M] extends PersistBase
{ /** All Sequences are persisted with the "Seq" type. */
  override def typeStr: String = "Seq"
}

/** All logical sequence classes are shown as "Seq"s. There encoding in memory and the immutability are irrelevant for their persistence. */
trait ShowSeq[A, R] extends ShowSeqLike[A, R] with PersistBaseSeq[A, R]

trait ShowIterable[A, R <: Iterable[A]] extends ShowSeq[A, R]
{ override def syntaxDepth(obj: R): Int = obj.foldLeft[Int](1)((acc: Int, el: A) => acc.max(evA.syntaxDepth(el))) + 1
  override def showForeach(obj: R, f: A => Unit): Unit = obj.foreach(f)
}

/** [[Show] type class for showing [[Sequ]][A] objects. */
trait ShowSequ[A, R <: Sequ[A]] extends ShowSeq[A, R]
{ override def syntaxDepth(obj: R): Int = obj.foldLeft(1)((acc, a) => acc.max(evA.syntaxDepth(a)))
  override def showForeach(obj: R, f: A => Unit): Unit = obj.foreach(f)
}

object ShowSequ
{
  def apply[A, R <: Sequ[A]]()(implicit evAIn: Show[A]): ShowSequ[A, R] = new ShowSequ[A, R]
  { override val evA: Show[A] = evAIn
  }
}

/** [[Show] type class for showing [[Sequ]][A] objects. */
trait ShowSeqSpec[A, R <: SeqSpec[A]] extends ShowSeq[A, R]
{ override def showForeach(obj: R, f: A => Unit): Unit = obj.ssForeach(f)
}

object ShowSeqSpec
{
  def apply[A, R <: SeqSpec[A]](typeStrIn: String)(implicit evAIn: Show[A]): ShowSeqSpec[A, R] = new ShowSeqSpec[A, R]
  { override val typeStr: String = typeStrIn
    override val evA: Show[A] = evAIn
  }
}
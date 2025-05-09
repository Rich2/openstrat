/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** [[Show]] type class for showing [[Sequ]][Ae] objects. */
trait ShowSeqSpec[Ae, A <: SeqSpec[Ae]] extends ShowSeqLike[Ae, A]
{ override def showForeach(obj: A, f: Ae => Unit): Unit = obj.foreach(f)
}

object ShowSeqSpec
{ /** Factory apply method for constructing [[Show]] type class instances / evidence for [[SeqSpec]] objects. */
  def apply[Ae, A <: SeqSpec[Ae]](typeStrIn: String)(using evAeIn: Show[Ae]): ShowSeqSpec[Ae, A] = new ShowSeqSpec[Ae, A]
  { override val typeStr: String = typeStrIn
    override val showAeEv: Show[Ae] = evAeIn
  }
}

/** Both [[Show]] and [[Unshow]] type class instances / evidence for [[SeqSpec]] objects. */
class PersistSeqSpecBoth[Ae, A <: SeqSpec[Ae]](val typeStr: String, val showAeEv: Show[Ae],  val unshowAeEv: Unshow[Ae])(using val build: BuilderMap[Ae, A])
  extends PersistBoth[A], ShowSeqSpec[Ae, A], UnshowSeqLike[Ae, A]

object PersistSeqSpecBoth
{  /** Factory apply method for constructing both [[Show]] and [[Unshow]] type class instances for [[SeqSpec]] types. */
  def apply[Ae, A <: SeqSpec[Ae]](typeStr: String)(using showAeEv: Show[Ae],  unshowAeEv: Unshow[Ae], build: BuilderMap[Ae, A]): PersistSeqSpecBoth[Ae, A] =
    new PersistSeqSpecBoth[Ae, A](typeStr, showAeEv,  unshowAeEv)
}
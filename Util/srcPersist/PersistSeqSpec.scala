/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat


/** [[Show]] type class for showing [[Sequ]][Ae] objects. */
trait ShowSeqSpec[Ae, A <: SeqSpec[Ae]] extends ShowSeqLike[Ae, A]
{ override def showForeach(obj: A, f: Ae => Unit): Unit = obj.ssForeach(f)
}

object ShowSeqSpec
{ /** Factory apply method for constructing [[ShowSeqSpec]] type class instances. */
  def apply[Ae, A <: SeqSpec[Ae]](typeStrIn: String)(implicit evAIn: Show[Ae]): ShowSeqSpec[Ae, A] = new ShowSeqSpec[Ae, A]
  { override val typeStr: String = typeStrIn
    override val showAeEv: Show[Ae] = evAIn
  }
}

class PersistSeqSpecBoth[Ae, A <: SeqSpec[Ae]](val typeStr: String, val showAeEv: Show[Ae],  val unshowAeEv: Unshow[Ae])(implicit
  val build: BuilderCollMap[Ae, A]) extends PersistBoth[A] with ShowSeqSpec[Ae, A] with UnshowSeqLike[Ae, A]

object PersistSeqSpecBoth
{
  def apply[Ae, A <: SeqSpec[Ae]](typeStr: String)(implicit showAeEv: Show[Ae],  unshowAeEv: Unshow[Ae], build: BuilderCollMap[Ae, A]):
    PersistSeqSpecBoth[Ae, A] = new PersistSeqSpecBoth[Ae, A](typeStr, showAeEv,  unshowAeEv)(build)
}
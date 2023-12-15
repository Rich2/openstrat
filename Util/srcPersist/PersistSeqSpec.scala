/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

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

class PersistSeqSpecBoth[Ae, A <: SeqSpec[Ae]](val typeStr: String, val showAeEv: Show[Ae],  val unshowAeEv: Unshow[Ae]) extends PersistBoth[A] with ShowSeqSpec[Ae, A]
  with UnshowSeqLike[Ae, A]
{
  override def build: BuilderCollMap[Ae, A] = ???
}
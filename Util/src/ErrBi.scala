/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.annotation.unchecked.uncheckedVariance, pParse.*, reflect.ClassTag

/** Biased bifunctor for errors. */
sealed trait ErrBi[+E <: Throwable, +A]
{ /** Classic map function as we see on a Scala [[Option]] or [[List]]. */
  def map[B](f: A => B): ErrBi[E, B]

  /** Classic flatMap function as we see on a Scala [[Option]]. */
  def flatMap[EE >: E <: Throwable, B](f: A => ErrBi[EE, B]): ErrBi[EE, B]

  /** Classic flatMap function taking a function from A => [[Option]][B] rather than the standard [[ErrBi]] of B. */
  def flatOptMap[B](f: A => Option[B]): ErrBi[E | ExcNFT, B] = this match
  { case succ: Succ[A] => f(succ.value).fld(FailNotFound, b => Succ(b))
    case fail: Fail[?] => fail
  }

  /** If this [[ErrBi]] is a [[Succ]] produce and [[ErrBi]] accumulator with the parameter function. */
  def flatMapAcc[EE >: E <: Throwable, B](f: A => ErrBiAcc[EE, B])(using ctE: ClassTag[EE] @uncheckedVariance, ctB: ClassTag[B] @uncheckedVariance):
    ErrBiAcc[EE, B]

  /** True if this is a successful [[Succ]] value. */
  def isSucc: Boolean

  /** True if this is a fail error. */
  def isFail: Boolean

  /** Will perform action if success. Does nothing if [[Fail]]. */
  def forSucc(f: A => Unit): Unit

  /** Fold this [[ErrBi]] into a type B. Takes two function parameters, one converts from A to B as in a normal map method. The second parameter in its own
   * parameter list converts from the Error type to type B. */
  @inline def fold[B](fFail: E => B)(fSucc: A => B): B

  /** Alternative fold, that only takes one parameter list. this [[ErrBi]] into a type B. Takes two function parameters, one converts from A to B as in a normal
   *  map method. The second parameter in its own parameter list converts from the Error type to type B. */
  @inline def fld[B](fFail: E => B, fSucc: A => B): B

  /** Gets the value of Good or returns the elseValue parameter if Bad. Both Good and Bad should be implemented in the leaf classes to avoid unnecessary boxing
   * of primitive values. */
  final def getElse(elseValue: A @uncheckedVariance): A = this match
  { case Succ(a) => a
    case _ => elseValue
  }

  /** Returns this [[ErrBi]] if it is a [[Succ]] else returns the parameter [[ErrBi]], which may be a [[Succ]] or [[Fail]]. */
  def orElse[E2 <: Throwable](elseVal: => ErrBi[E2, A] @uncheckedVariance): ErrBi[E2, A] = this match
  { case succ: Succ[A] => succ
    case _ => elseVal
  }
  /** Returns this if success, else returns the other [[ErrBi]]. */
  def succOrOther[EE >: E <: Throwable, AA >: A](otherErrBi: => ErrBi[EE, AA] @uncheckedVariance): ErrBi[EE, AA]

  /** This is just a Unit returning fold, but is preferred because the method  is explicit that it is called for effects, rather than to return a value. This
   * method is implemented in the leaf [[Succ]] and [[Fail]] classes to avoid boxing. */
  def forFold(fErr: E => Unit)(fSucc: A => Unit): Unit

  /** This is just a Unit returning fold, but is preferred because the method  is explicit that it is called for effects, rather than to return a value. This
   * method is implemented in the leaf [[Succ]] and [[Fail]] classes to avoid boxing. */
  def forFld(fErr: E => Unit, fSucc: A => Unit): Unit

  def get: A = this match
  { case succ: Succ[A] => succ.value
    case fail: Fail[E] => throw(Exception("Attempting to get value from a Fail with " + fail.error.toString))
  }
  
  def reportStr: String
}

object ErrBi
{
  def map2[E <: Throwable, A1, A2, B](eb1: ErrBi[E, A1], eb2: ErrBi[E, A2])(f: (A1, A2) => B): ErrBi[E, B] = eb1.flatMap(a1 => eb2.map(a2 => f(a1, a2)))

  def map3[E <: Throwable, A1, A2, A3, B](eb1: ErrBi[E, A1], eb2: ErrBi[E, A2], eb3: ErrBi[E, A3])(f: (A1, A2, A3) => B): ErrBi[E, B] =
    for { s1 <- eb1; s2 <- eb2; s3 <- eb3 } yield f(s1, s2, s3)

  def map4[E <: Throwable, A1, A2, A3, A4, B](eb1: ErrBi[E, A1], eb2: ErrBi[E, A2], eb3: ErrBi[E, A3], eb4: ErrBi[E, A4])(f: (A1, A2, A3, A4) => B):
    ErrBi[E, B] = for { s1 <- eb1; s2 <- eb2; s3 <- eb3; s4 <- eb4 } yield f(s1, s2, s3, s4)

  def map5[E <: Throwable, A1, A2, A3, A4, A5, B](eb1: ErrBi[E, A1], eb2: ErrBi[E, A2], eb3: ErrBi[E, A3], eb4: ErrBi[E, A4], eb5: ErrBi[E, A5])(
    f: (A1, A2, A3, A4, A5) => B): ErrBi[E, B] =
    for { s1 <- eb1; s2 <- eb2; s3 <- eb3; s4 <- eb4; s5 <- eb5 } yield f(s1, s2, s3, s4, s5)

  def map6[E <: Throwable, A1, A2, A3, A4, A5, A6, B](eb1: ErrBi[E, A1], eb2: ErrBi[E, A2], eb3: ErrBi[E, A3], eb4: ErrBi[E, A4], eb5: ErrBi[E, A5],
    eb6: ErrBi[E, A6])(f: (A1, A2, A3, A4, A5, A6) => B): ErrBi[E, B] =
    for { s1 <- eb1; s2 <- eb2; s3 <- eb3; s4 <- eb4; s5 <- eb5; s6 <- eb6 } yield f(s1, s2, s3, s4, s5, s6)

  given eqTEv[E <: Throwable, A](using evA: EqT[A]): EqT[ErrBi[E, A]] = (em1, em2) => (em1, em2) match
  { case (Succ(a1), Succ(a2)) => evA.eqT(a1, a2)
    case (Fail(err1), Fail(err2)) => err1 == err2
    case _ => false
  }

  extension[E <: Throwable](thisErrBi: ErrBi[E, String])
  { /** Extension method to map this [[ErrBi]] String to find a value of the given type from the String parsed as RSON. */
    def findType[A](using ev: Unshow[A]): ErrBi[Throwable, A] = thisErrBi.flatMap(str => stringToStatements(str).flatMap(_.findType[A]))

    /** Extension method to map this [[ErrBi]] String to find a value of the given type from the String parsed as RSON or return the elseValue if that fails. */
    def findTypeElse[A](elseValue: => A)(using ev: Unshow[A]): A = findType[A].getElse(elseValue)

    /** Extension method to map this [[ErrBi]] String to find a value of the given type from the String parsed as RSON and then perform a foreach on the value
     *  if successful. */
    def findTypeForeach[A: Unshow](f: A => Unit): Unit = findType[A].forSucc(f)

    def findSetting[A](settingStr: String)(using ev: Unshow[A]): ErrBi[Throwable, A] =
      thisErrBi.flatMap(str => stringToStatements(str).flatMap(_.findSetting[A](settingStr)))

    def findSettingElse[A: Unshow](settingStr: String, elseValue: => A): A = findSetting[A](settingStr).getElse(elseValue)

    def findSomeSetting[A: Unshow](settingStr: String, elseValue: => A): A = ??? //findSetting[Option[A]](settingStr)(using ev: Persist[A]): EMon[A]

    def findSomeSettingElse[A: Unshow](settingStr: String, elseValue: => A): A = ??? //findSetting[A](settingStr).getElse(elseValue)
  }
}

/** Success, boxes a good value of the desired type. */
case class Succ[+A](val value: A) extends ErrBi[Nothing, A]
{ override def map[B](f: A => B): ErrBi[Nothing, B] = new Succ[B](f(value))

  override def flatMap[EE <: Throwable, B](f: A => ErrBi[EE, B]): ErrBi[EE, B] = f(value) match
  { case succ: Succ[B] => succ
    case fail: Fail[EE] => fail
  }

  override def flatMapAcc[EE <: Throwable, B](f: A => ErrBiAcc[EE, B])(using ctE: ClassTag[EE] @uncheckedVariance, ctB: ClassTag[B] @uncheckedVariance):
    ErrBiAcc[EE, B] = f(value)
  
  override def isSucc: Boolean = true
  override def isFail: Boolean = false
  override def forSucc(f: A => Unit): Unit = f(value)
  override def fold[B](fFail: Nothing => B)(fSucc: A => B): B = fSucc(value)
  override def fld[B](fFail: Nothing => B, fSucc: A => B): B = fSucc(value)
  override def succOrOther[EE >: Nothing <: Throwable, AA >: A](otherErrBi: => ErrBi[EE, AA]): ErrBi[EE, AA] = this
  override def forFold(fErr: Nothing => Unit)(fSucc: A => Unit): Unit = fSucc(value)
  override def forFld(fErr: Nothing => Unit, fSucc: A => Unit): Unit = fSucc(value)
  override def hashCode(): Int = 11 + 13 * value.hashCode()
  override def reportStr: String = value match{ case er: EffectReport => er.reportStr; case a => a.toString }

  override def equals(that: Any): Boolean = that match
  { case op: Succ[?] if value == op.value => true
    case _ => false
  }
}

object Succ
{ /** Factory apply method for success wraps a raw value. */
  def apply[A](value: A): Succ[A] = new Succ[A](value)

  /** Extractor for success. */
  def unapply[A](inp: ErrBi[?, A]): Option[A] = inp match
  { case sa: Succ[A] => Some(sa.value)
    case _ => None
  }

  /** Implicit evidence for [[EqT]] for type [[Succ]]. */
  given eqTEv[A](using evA: EqT[A]): EqT[Succ[A]] = (su1, su2) => evA.eqT(su1.value, su2.value)
}

/** Failure to return a value of the desired type. Boxes a [[Throwable]] error. */
class Fail[+E <: Throwable](val error: E) extends ErrBi[E, Nothing]
{ override def map[B](f: Nothing => B): ErrBi[E, B] = this
  override def flatMap[EE >: E <: Throwable, B](f: Nothing => ErrBi[EE, B]): ErrBi[EE, B] = this
  
  override def flatMapAcc[EE >: E <: Throwable, B](f: Nothing => ErrBiAcc[EE, B])(using ctE: ClassTag[EE] @uncheckedVariance,
    ctB: ClassTag[B] @uncheckedVariance): ErrBiAcc[EE, B] = new ErrBiAcc[EE, B](Array[EE](error), Array[B]())
  
  override def isSucc: Boolean = false
  override def isFail: Boolean = true
  override def forSucc(f: Nothing => Unit): Unit = {}
  override def fold[B](fFail: E => B)(fSucc: Nothing => B): B = fFail(error)
  override def fld[B](fFail: E => B, fSucc: Nothing => B): B = fFail(error)
  override def succOrOther[EE >: E <: Throwable, AA >: Nothing](otherErrBi: => ErrBi[EE, AA]): ErrBi[EE, AA] = otherErrBi
  override def forFold(fErr: E => Unit)(fSucc: Nothing => Unit): Unit = fErr(error)
  override def forFld(fErr: E => Unit, fSucc: Nothing => Unit): Unit = fErr(error)
  override def reportStr: String = error.toString

  override def equals(obj: Any): Boolean = obj match{
    case fail: Fail[?] => error == fail.error
    case _ => false
  }

  override def toString: String = "FailExc" + error.toString.enParenth
  override def hashCode(): Int = "Fail".hashCode + 31 * error.hashCode
}

object Fail
{ /** Factory apply method for [[Fail].] */
  def apply[E <: Throwable](error: E): Fail[E] = new Fail[E](error)

  /** Extractor unapply method for [[Fail]]. */
  def unapply[E <: Throwable](inp: ErrBi[E, ?]): Option[E] = inp match{
    case fail: Fail[E] => Some(fail.error)
    case _ => None
  }
}

/** A Throwable error monad. */
type ThrowMon[+A] = ErrBi[Throwable, A]

/** A Throwable error monad with an [[Arr]] for success. */
type ThrowMonArr[+A] = ErrBi[Throwable, Arr[A]]

/** A Throwable error monad with an [[RArr]] for success. */
type ThrowMonRArr[+A] = ErrBi[Throwable, RArr[A]]

/** An [[Exception]] error monad. */
type ExcMon[+A] = ErrBi[Exception, A]

object FailExc
{ /** Factory apply method to construct a [[Fail]] with an [[Exception]] type. */
  @inline def apply[A](message: String): FailExc = new Fail[Exception](new Exception(message))
}

/** Java IO [[Exception]] */
type IOExc = java.io.IOException

/** A [[java.io.IOException]] error monad. */
type ExcIOMon[+A] = ErrBi[IOExc, A]

/** A [[Fail]] with [[Exception]] type. */
type FailExc = Fail[Exception]

/** A [[Fail]] with [[Exception]] type. */
type FailIO = Fail[IOExc]

object FailIO
{ /** Factory apply method to construct a [[Fail]] from an [[IOException]] type. */
  @inline def apply[A](err: IOExc): FailIO = new Fail[IOExc](err)

  /** Factory apply method to construct a [[Fail]] with an [[IOException]] type. */
  @inline def apply[A](message: String): FailIO = new Fail[IOExc](new IOExc(message))
}

object NoneExc extends Exception("None")

/** Error bifunctor for [[RArr]] values. */
type ErrBiArr[E <: Throwable, AE <: AnyRef] = ErrBi[E, RArr[AE]]

/** Extractor function object for a successful Arr Sequence of length 1. */
object SuccArr1
{ /** Extractor method for a successful [[Arr]] Sequence of length 1. */
  def unapply[A <: AnyRef](eArr: ErrBiArr[?, A]): Option[A] = eArr match
  { case Succ(Arr1(head)) => Some(head)
    case _ => None
  }
}

type ExcMonArr[Ae] = ErrBi[Exception, Arr[Ae]]
type ExcMonRArr[Ae] = ErrBi[Exception, RArr[Ae]]

/** Error bifunctor for [[Tuple2]]. */
type ErrBi2[E <: Throwable, A1, A2] = ErrBi[E, (A1, A2)]

/** Extension class for [[Exception]] bifunctor for [[Tuple2]]s. */
extension [E <: Throwable, A1, A2](thisEE2: ErrBi2[E, A1, A2])
{
  def t2FlatMap[B1, B2](f: (A1, A2) => ErrBi2[E, B1, B2]): ErrBi2[E, B1, B2] = thisEE2 match
  { case Succ2(a1, a2) => f(a1, a2)
    case Fail(err) => Fail(err)
    case eb => excep(s"$eb This case was unexpected")
  }
}

/** Success for a [[Tuple2]] value. */
type Succ2[A1, A2] = Succ[(A1, A2)]

object Succ2
{ /** Factory apply method for creating [[Succ]] with a [[Tuple2]] value. */
  def apply[A1, A2](a1: A1, a2: A2): Succ2[A1, A2] = new Succ[(A1, A2)]((a1, a2))

  /** unapply extractor for success on an [[ErrBi]] with a [[Tuple2]] value type. */
  def unapply[A1, A2](inp: ErrBi2[?, A1, A2]): Option[(A1, A2)] = inp match
  { case succ: Succ2[A1, A2] => Some(succ.value._1, succ.value._2)
    case _ => None
  }
}

/** Error bifunctor for [[Tuple3]]. */
type ErrBi3[E <: Throwable, A1, A2, A3] = ErrBi[E, (A1, A2, A3)]

/** Extension class for [[Exception]] bifunctor for [[Tuple3]]s. */
extension[E <: Throwable, A1, A2, A3](thisEE3: ErrBi3[E, A1, A2, A3])
{
  def flatMap3[B1, B2, B3](f: (A1, A2, A3) => ErrBi3[E, B1, B2, B3]): ErrBi3[E, B1, B2, B3] = thisEE3 match
  { case Succ3(a1, a2, a3) => f(a1, a2, a3)
    case Fail(err) => Fail(err)
    case eb => excep(s"$eb This case was unexpected")
  }
}

/** Success for a [[Tuple3]] value. */
type Succ3[A1, A2, A3] = Succ[(A1, A2, A3)]

object Succ3
{ /** Factory apply method for creating [[Succ]] with a [[Tuple3]] value. */
  def apply[A1, A2, A3](a1: A1, a2: A2, a3: A3): Succ3[A1, A2, A3] = new Succ[(A1, A2, A3)]((a1, a2, a3))

  /** unapply extractor for success on an [[ErrBi]] with a [[Tuple3]] value type. */
  def unapply[A1, A2, A3](inp: ErrBi3[?, A1, A2, A3]): Option[(A1, A2, A3)] = inp match
  { case succ: Succ3[A1, A2, A3] => Some(succ.value._1, succ.value._2, succ.value._3)
    case _ => None
  }
}
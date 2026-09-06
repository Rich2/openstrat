/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.annotation.unchecked.uncheckedVariance, pParse.*, reflect.ClassTag

type Succ[B] = Right[Nothing, B]

object Succ
{ def apply[B](value: B): Succ[B] = Right[Nothing, B](value)
}

type Fail[E] = Left[E, Nothing]

object Fail
{ def apply[E](value: E): Fail[E] = Left[E, Nothing](value)
}

/** Extension methods for [[Either]]. */
implicit class EitherExts[E, A](val thisEither: Either[E, A])
{
  def fold[B](fe: E => B)(fa: A => B): B = thisEither match
  { case Left[E, A](err) => fe(err)
    case Right(value) => fa(value)
  }

  /** Returns this if success, else returns the other [[Either]]. */
  def succOrOther[EE >: E <: Throwable, AA >: A](otherEither: => Either[EE, AA] @uncheckedVariance): Either[EE, AA] = thisEither match {
    case Right(_) => thisEither
    case Left(_) => otherEither
  }

  /** Classic flatMap function taking a function from A => [[Option]][B] rather than the standard [[Either]] of B. */
  def flatOptMap[B](f: A => Option[B]): Either[E | ExcNFT, B] = thisEither match
  { case Right(value) => f(value).fld(FailNotFound, b => Right(b))
    case Left(err) => Fail(err)
  }
}

implicit class Eitherthrowable[E <: Throwable, A](thisEither: Either[E, A])
{ /** If this [[Either]] is a [[Right]] produce [[ErrBiAcc]] with the parameter function. If this is [[Left]] produce [[ErrBiAcc]] with this single [[Left]]. */
  def mapAcc[EE >: E <: Throwable, B](f: A => ErrBiAcc[EE, B])(using ctE: ClassTag[EE] @uncheckedVariance, ctB: ClassTag[B] @uncheckedVariance):
    ErrBiAcc[EE, B] = thisEither match
  { case Right(value) => f(value)
    case Left(err) => new ErrBiAcc[EE, B](Array[EE](err), Array[B]())
  }
  
  def reportStr: String = thisEither match
  { case Right(value) => value match
    { case er: EffectReport => er.reportStr
      case a => a.toString
    }
    case Left(err) => err.toString
  }
}

implicit class EitherStringExts[E <: Throwable](thisEither: Either[E, String])
{/** Extension method tWo map this [[Either]] String to find a value of the given type from the String parsed as RSON. */
  def findType[A](using ev: Unshow[A]): Either[Throwable, A] = thisEither.flatMap(str => stringToStatements(str).flatMap(_.findType[A]))

  /** Extension method to map this [[Either]] String to find a value of the given type from the String parsed as RSON or return the elseValue if that fails. */
  def findTypeElse[A](elseValue: => A)(using ev: Unshow[A]): A = findType[A].getOrElse(elseValue)

  /** Extension method to map this [[Either]] String to find a value of the given type from the String parsed as RSON and then perform a foreach on the value
   * if successful. */
  def findTypeForeach[A: Unshow](f: A => Unit): Unit = findType[A].foreach(f)

  def findSetting[A](settingStr: String)(using ev: Unshow[A]): Either[Throwable, A] =
    thisEither.flatMap(str => stringToStatements(str).flatMap(_.findSetting[A](settingStr)))

  def findSettingElse[A: Unshow](settingStr: String, elseValue: => A): A = findSetting[A](settingStr).getOrElse(elseValue)

  def findSomeSetting[A: Unshow](settingStr: String, elseValue: => A): A = ??? //findSetting[Option[A]](settingStr)(using ev: Persist[A]): EMon[A]

  def findSomeSettingElse[A: Unshow](settingStr: String, elseValue: => A): A = ??? //findSetting[A](settingStr).getElse(elseValue)
}

extension (obj: Either.type)
{ /** If both [[Errbi]] inputs are [[Right]]s return [[Right]] of function. If both [[Left]]s combine the errors. Error type may widen to contain all the
   * possibilities */
  def map2[E <: Throwable, A1, A2, B](eb1: Either[E, A1], eb2: Either[E, A2])(f: (A1, A2) => B): Either[Throwable, B] = eb1 match
  { case Right(a1) => eb2.map(a2 => f(a1, a2))
    case Left(err1) => eb2 match
    { case Right(_) => Left(err1)
      case Left(err2) => Left(ThrowMulti(err1, err2))
    }
  }

  /** If both [[Errbi]] inputs are [[Right]]s return the result of the function. If both [[Left]]s combine the errors. Error type may widen to contain all the
   * possibilities */
  def flatMap2[E <: Throwable, A1, A2, B](eb1: Either[E, A1], eb2: Either[E, A2])(f: (A1, A2) => Either[Throwable, B]): Either[Throwable, B] = eb1 match
  { case Right(a1) => eb2.flatMap(a2 => f(a1, a2))
    case Left(err1) => eb2 match
    { case Right(_) => Left(err1)
      case Left(err2) => Left(ThrowMulti(err1, err2))
    }
  }

  /** If this [[Either]] is a [[Right]] produce [[ErrBiAcc]] with the parameter function. If this is [[Left]] produce [[ErrBiAcc]] with this single [[Left]]. */
  def map2Acc[E <: Throwable, A1, A2, B](eb1: Either[E, A1], eb2: Either[E, A2])(f: (A1, A2) => ErrBiAcc[E, B])(using ctE: ClassTag[E] @uncheckedVariance,
    ctB: ClassTag[B] @uncheckedVariance): ErrBiAcc[E, B] = eb1 match
  { 
    case Right(a1) =>  eb2 match
    { case Right(a2) => f(a1, a2)
      case Left(e2) => ErrBiAcc.err1(e2)
    }
    case Left(e1) => eb2 match
    { case Right(_) => ErrBiAcc.err1(e1)
      case Left(e2) => ErrBiAcc.errs2(e1, e2)
    }
  }
  
  def map5[E <: Throwable, A1, A2, A3, A4, A5, B](eb1: Either[E, A1], eb2: Either[E, A2], eb3: Either[E, A3], eb4: Either[E, A4], eb5: Either[E, A5])(
    f: (A1, A2, A3, A4, A5) => B): Either[E, B] =
    for { s1 <- eb1; s2 <- eb2; s3 <- eb3; s4 <- eb4; s5 <- eb5 } yield f(s1, s2, s3, s4, s5)

  def map6[E <: Throwable, A1, A2, A3, A4, A5, A6, B](eb1: Either[E, A1], eb2: Either[E, A2], eb3: Either[E, A3], eb4: Either[E, A4], eb5: Either[E, A5],
    eb6: Either[E, A6])(f: (A1, A2, A3, A4, A5, A6) => B): Either[E, B] =
    for { s1 <- eb1; s2 <- eb2; s3 <- eb3; s4 <- eb4; s5 <- eb5; s6 <- eb6 } yield f(s1, s2, s3, s4, s5, s6)
}
def EitherMap3[E <: Throwable, A1, A2, A3, B](eb1: Either[E, A1], eb2: Either[E, A2], eb3: Either[E, A3])(f: (A1, A2, A3) => B): Either[E, B] =
  for { s1 <- eb1; s2 <- eb2; s3 <- eb3 } yield f(s1, s2, s3)


def EitherMap4[E <: Throwable, A1, A2, A3, A4, B](eb1: Either[E, A1], eb2: Either[E, A2], eb3: Either[E, A3], eb4: Either[E, A4])(f: (A1, A2, A3, A4) => B):
Either[E, B] = for {s1 <- eb1; s2 <- eb2; s3 <- eb3; s4 <- eb4} yield f(s1, s2, s3, s4)

/** An [[Either]] with a [[Throwable]] [[Left]] type. */
type ThrowEither[+A] = Either[Throwable, A]

/** A Throwable error monad with an [[RArr]] for success. */
type ThrowEitherRArr[+A] = Either[Throwable, RArr[A]]

/** An [[Exception]] error monad. */
type ExcEither[+A] = Either[Exception, A]

object LeftExc
{ /** Factory apply method to construct a [[Left]] with an [[Exception]] type. */
  @inline def apply(message: String): LeftExc = new Left[Exception, Nothing](new Exception(message))
}

/** Java IO [[Exception]] */
type IOExc = java.io.IOException

/** A [[java.io.IOException]] error monad. */
type IOExcEither[+B] = Either[IOExc, B]

/** A [[Left]] with [[Exception]] type. */
type LeftExc = Left[Exception, Nothing]

/** A [[Left]] with [[IOException]] type. */
type LeftIO = Left[IOExc, Nothing]

object FailIO
{ /** Factory apply method to construct a [[Left]] from an [[java.io.IOException]] type. */
  @inline def apply(err: IOExc): LeftIO = new Left[IOExc, Nothing](err)

  /** Factory apply method to construct a [[Left]] with an [[java.io.IOException]] type. */
  @inline def apply(message: String): LeftIO = new Left[IOExc, Nothing](new IOExc(message))
}

/** A [[None]] value converted to an [[Extension]]. */
object NoneExc extends Exception("None")


/** Error bifunctor for [[Tuple2]]. */
type throwEitherT2[E <: Throwable, A1, A2] = Either[E, (A1, A2)]

/** Extension class for [[Exception]] bifunctor for [[Tuple2]]s. */
extension [E <: Throwable, A1, A2](thisEE2: throwEitherT2[E, A1, A2])
{
  def t2FlatMap[B1, B2](f: (A1, A2) => throwEitherT2[E, B1, B2]): throwEitherT2[E, B1, B2] = thisEE2 match
  { case Succ2(a1, a2) => f(a1, a2)
    case Left(err) => Left(err)
    case eb => excep(s"$eb This case was unexpected")
  }
}

/** Success for a [[Tuple2]] value. */
type Succ2[B1, B2] = Right[Nothing, (B1, B2)]

object Succ2
{ /** Factory apply method for creating [[Right]] with a [[Tuple2]] value. */
  def apply[B1, B2](b1: B1, b2: B2): Succ2[B1, B2] = new Right[Nothing, (B1, B2)]((b1, b2))

  /** unapply extractor for success on an [[Either]] with a [[Tuple2]] value type. */
  def unapply[B1, B2](inp: throwEitherT2[?, B1, B2]): Option[(B1, B2)] = inp match
  { case Right(pair) => Some(pair._1, pair._2)
    case _ => None
  }
}

/** Error bifunctor for [[Tuple3]]. */
type ErrBi3[E <: Throwable, A1, A2, A3] = Either[E, (A1, A2, A3)]

/** Extension class for [[Exception]] bifunctor for [[Tuple3]]s. */
extension[E <: Throwable, A1, A2, A3](thisEE3: ErrBi3[E, A1, A2, A3])
{
  def flatMap3[B1, B2, B3](f: (A1, A2, A3) => ErrBi3[E, B1, B2, B3]): ErrBi3[E, B1, B2, B3] = thisEE3 match
  { case Succ3(a1, a2, a3) => f(a1, a2, a3)
    case Left(err) => Left(err)
    case eb => excep(s"$eb This case was unexpected")
  }
}

/** Success for a [[Tuple3]] value. */
type Succ3[B1, B2, B3] = Right[Nothing, (B1, B2, B3)]

object Succ3
{ /** Factory apply method for creating [[Right]] with a [[Tuple3]] value. */
  def apply[B1, B2, B3](b1: B1, b2: B2, b3: B3): Succ3[B1, B2, B3] = new Right[Nothing, (B1, B2, B3)]((b1, b2, b3))

  /** unapply extractor for success on an [[Either]] with a [[Tuple3]] value type. */
  def unapply[B1, B2, B3](inp: ErrBi3[?, B1, B2, B3]): Option[(B1, B2, B3)] = inp match
  { case Right(tuple) => Some(tuple._1, tuple._2, tuple._3)
    case _ => None
  }
}
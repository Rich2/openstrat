/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.annotation.unchecked.uncheckedVariance, pParse.*, reflect.ClassTag

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
  def flatOptMap[B](f: A => Option[B]): Either[E | ExcNotFound.type, B] = thisEither match
  { case Right(value) => f(value).fld(NotFoundLeft, b => Right(b))
    case Left(err) => Left(err)
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

implicit class EitherStringExts[E <: Exception](thisEither: Either[E, String])
{/** Extension method two map this [[Either]] String to find a value of the given type from the String parsed as RSON. */
  def findType[A](using ev: Unshow[A]): ExcEither[A] = thisEither.flatMap(str => stringToStatements(str).flatMap(_.findType[A]))

  /** Extension method to map this [[Either]] String to find a value of the given type from the String parsed as RSON or return the elseValue if that fails. */
  def findTypeElse[A](elseValue: => A)(using ev: Unshow[A]): A = findType[A].getOrElse(elseValue)

  /** Extension method to map this [[Either]] String to find a value of the given type from the String parsed as RSON and then perform a foreach on the value
   * if successful. */
  def findTypeForeach[A: Unshow](f: A => Unit): Unit = findType[A].foreach(f)

  def findSetting[A](settingStr: String)(using ev: Unshow[A]): ExcEither[A] ={
    val res1: Either[ParseException | E, RArr[Statement]] = thisEither.flatMap(str => stringToStatements(str))
    res1.flatMap(_.findSetting[A](settingStr))
  }

  def findSettingElse[A: Unshow](settingStr: String, elseValue: => A): A = findSetting[A](settingStr).getOrElse(elseValue)

  def findSomeSetting[A: Unshow](settingStr: String, elseValue: => A): A = ??? //findSetting[Option[A]](settingStr)(using ev: Persist[A]): EMon[A]

  def findSomeSettingElse[A: Unshow](settingStr: String, elseValue: => A): A = ??? //findSetting[A](settingStr).getElse(elseValue)
}

extension (obj: Either.type)
{ /** If both [[Errbi]] inputs are [[Right]]s return [[Right]] of function. If both [[Left]]s combine the errors. Error type may widen to contain all the
   * possibilities */
  def map2[E <: Throwable, A1, A2, B](eb1: Either[E, A1], eb2: Either[E, A2])(f: (A1, A2) => B)(using ClassTag[E]): Either[ThrowMulti[E], B] = eb1 match
  { case Right(a1) => eb2.match
    { case Right(a2) => Right(f(a1, a2))
      case Left(err) => Left(ThrowMulti(err))
    }
    case Left(err1) => eb2 match
    { case Right(_) => Left(ThrowMulti(err1))
      case Left(err2) => Left(ThrowMulti(err1, err2))
    }
  }

  /** If both [[Errbi]] inputs are [[Right]]s return the result of the function. If both [[Left]]s combine the errors. Error type may widen to contain all the
   * possibilities */
  def flatMap2[E <: Throwable, A1, A2, B](eb1: Either[E, A1], eb2: Either[E, A2])(f: (A1, A2) => Either[Throwable, B])(using ClassTag[E]):
    Either[Throwable, B] = eb1 match
  { case Right(a1) => eb2.flatMap(a2 => f(a1, a2))
    case Left(err1) => eb2 match
    { case Right(_) => Left(err1)
      case Left(err2) => Left(ThrowMulti(err1, err2))
    }
  }

  /** If this [[Either]] is a [[Right]] produce [[ErrBiAcc]] with the parameter function. If this is [[Left]] produce [[ErrBiAcc]] with this single [[Left]]. */
  def map2Acc[E <: Throwable, A1, A2, B](eb1: Either[E, A1], eb2: Either[E, A2])(f: (A1, A2) => ErrBiAcc[E, B])(using ctE: ClassTag[E] @uncheckedVariance,
    ctB: ClassTag[B] @uncheckedVariance): ErrBiAcc[E, B] = eb1 match {
    case Right(a1) => eb2 match {
      case Right(a2) => f(a1, a2)
      case Left(e2) => ErrBiAcc.err1(e2)
    }
    case Left(e1) => eb2 match {
      case Right(_) => ErrBiAcc.err1(e1)
      case Left(e2) => ErrBiAcc.errs2(e1, e2)
    }
  }

  /** If all 3 [[Either]] inputs are [[Right]]s return [[Right]] of function. If both [[Left]]s combine the errors. Error type may widen to contain all the
   * possibilities */
  def map3[E <: Throwable, A1, A2, A3, B](eb1: Either[E, A1], eb2: Either[E, A2], eb3: Either[E, A3])(f: (A1, A2, A3) => B): Either[E, B] =
  { for
    { s1 <- eb1
      s2 <- eb2
      s3 <- eb3
    }
    yield f(s1, s2, s3)
  }

  def map4[E <: Throwable, A1, A2, A3, A4, B](eb1: Either[E, A1], eb2: Either[E, A2], eb3: Either[E, A3], eb4: Either[E, A4])(f: (A1, A2, A3, A4) => B):
    Either[E, B] =
    for {s1 <- eb1; s2 <- eb2; s3 <- eb3; s4 <- eb4} yield f(s1, s2, s3, s4)
  
  def map5[E <: Throwable, A1, A2, A3, A4, A5, B](eb1: Either[E, A1], eb2: Either[E, A2], eb3: Either[E, A3], eb4: Either[E, A4], eb5: Either[E, A5])(
    f: (A1, A2, A3, A4, A5) => B): Either[E, B] =
    for { s1 <- eb1; s2 <- eb2; s3 <- eb3; s4 <- eb4; s5 <- eb5 } yield f(s1, s2, s3, s4, s5)

  def map6[E <: Throwable, A1, A2, A3, A4, A5, A6, B](eb1: Either[E, A1], eb2: Either[E, A2], eb3: Either[E, A3], eb4: Either[E, A4], eb5: Either[E, A5],
    eb6: Either[E, A6])(f: (A1, A2, A3, A4, A5, A6) => B): Either[E, B] =
    for { s1 <- eb1; s2 <- eb2; s3 <- eb3; s4 <- eb4; s5 <- eb5; s6 <- eb6 } yield f(s1, s2, s3, s4, s5, s6)


  /** Folds over 2 [[Either]]s. Takes 2 functions the first is used one or both [[Either]]s are [[Left]]s. The second function is only used if both are
   * [[Right]]s. */
  def fold2[E <: Exception, A1, A2, B](eth1: => Either[E, A1], eth2: => Either[E, A2])(fe: ExcMulti[E] => B)(fa: (A1, A2) => B)(using ctE: ClassTag[E]):
  B = eth1 match {
    case Right(a1) => eth2 match {
      case Right(a2) => fa(a1, a2)
      case Left(err2) => fe(ExcMulti(err2))
    }
    case Left(err1) => eth2 match {
      case Right(_) => fe(ExcMulti(err1))
      case Left(err2) => fe(ExcMulti(err1, err2))
    }
  }

  /** Folds over 2 [[Either]]s but the functions return [[Unit]]. Takes 2 functions the first is used if one or both [[Either]]s are [[Left]]s. The second
   * function is only used if both are [[Right]]s. */
  def forboth2[E <: Exception, A1, A2, U1, U2](eth1: Either[E, A1], eth2: Either[E, A2])(fe: ExcMulti[E] => U1)(fa: (A1, A2) => U2)(using ctE: ClassTag[E]):
  Unit = eth1 match
  { case Right(a1) => eth2 match
    { case Right(a2) => fa(a1, a2)
      case Left(err2) => fe(ExcMulti(err2))
    }
    case Left(err1) => eth2 match
    { case Right(_) => fe(ExcMulti(err1))
      case Left(err2) => fe(ExcMulti(err1, err2))
    }
  }  
}

/** Extension class for [[Exception]] bifunctor for [[Tuple2]]s. */
extension [E <: Throwable, A1, A2](thisEE2: throwEitherT2[E, A1, A2])
{
  def t2FlatMap[B1, B2](f: (A1, A2) => throwEitherT2[E, B1, B2]): throwEitherT2[E, B1, B2] = thisEE2 match
  { case Succ2(a1, a2) => f(a1, a2)
    case Left(err) => Left(err)
    case eb => excep(s"$eb This case was unexpected")
  }
}
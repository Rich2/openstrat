/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package utiljvm
import java.io.File, pweb.*

implicit class EitherUtilJvmExts[E, A](thisEither: Either[E, A]){
  def mapIterResults[EE, B, R <: Results[EE, B]](f: A => Iterable[Either[EE, B]])(using build: ResultsBuilder[EE, B, R]): Either[E, R] = thisEither.map(a => build.fromIterable(f(a)))
  
  def mapResults[EE, B, R <: Results[EE, B]](f: A => Sequ[Either[EE, B]])(using build: ResultsBuilder[EE, B, R]): Either[E, R] = thisEither.map(a => build.fromSequ(f(a)))
}
/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package utiljvm
import pweb.*, java.sql.*, org.postgresql.util.PSQLException

/** [[Either]] with a [[SQLException]] error type. */
type SqlExcEither[+A] = Either[SQLException, A]

type PsqlExcEither[+A] = Either[PSQLException, A]

/** Postgres table. */
case class Gable(name: String)
{
  def insert(dbRow: DBRow)(using conn: Connection): PsqlExcEither[Int] =
  {
    try {
      val stmt = conn.createStatement()
      val statementStr = "INSERT INTO" -- name -- "VALUES"  -- dbRow.values.mkStr(", ").enParenth
      val result = stmt.executeUpdate(statementStr)
      Right(result)
    }
    catch { case e: PSQLException => Left(e) }
  }
}

extension (thisExc: PSQLException)
{
  def uniqueFail: Boolean = thisExc.getSQLState == "23505"
}  
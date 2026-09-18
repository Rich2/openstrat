/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gres
import utiljvm.*, geom.*, pweb.*, webjvm.*, java.sql.*

type SqlExcEither[+A] = Either[SQLException, A]

val connStr = "jdbc:postgresql://localhost:5432/"

def postgresConn(name: String, password: String): SqlExcEither[Connection] =
  try{ Right(DriverManager.getConnection(connStr, name, password)) }
  catch{ case sqlExc: SQLException => Left(sqlExc) }

/** Postgres table. */
case class Gable(name: String)
{
  def insert(dbRow: DBRow)(using conn: Connection): ExcEither[Int] =
  {
    try {
      val stmt = conn.createStatement()
      val statementStr = "INSERT INTO" -- name -- "VALUES"  -- dbRow.values.mkStr(", ").enParenth
      val result = stmt.executeUpdate(statementStr)
      Right(result)
    }
    catch {
      case e: Exception => Left(e)
    }
  }
}
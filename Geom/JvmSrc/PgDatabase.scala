/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package utiljvm
import pweb.*, pParse.*, java.sql.*, org.postgresql.util.PSQLException

trait PgDatabase
{  
  def tables: RArr[Gable]
}

object PgDatabase
{
  def apply(tables: Gable*): PgDatabase = PgDatabaseGen(tables.toRArr)

  val connStr: String = "jdbc:postgresql://"

  /** Attempts to find the database name, username and password settings for o Postgres database. */
  def eSetts(connSettings: String): ParseExcEither[(Identifier, Identifier, String)] =
    connSettings.find3Settings[Identifier, Identifier, String]("database", "appUsername", "appPassword")

  /** Attempts to make a connection to the Postgres database. */
  def conn(dbName: String, uName: String, password: String, hostStr: String = "localhost", port: Int = 5432): PsqlExcEither[Connection] =
    try { Right(DriverManager.getConnection(connStr + hostStr + ":" + port.str / dbName, uName, password)) }
    catch { case sqlExc: PSQLException => Left(sqlExc) }

  /** Attempts tp finds the database name, username and password settings for a Postgres database, from the passed [[String]] and then attempts to make a
   * connection to the database. */
  def settConn(connSettings: String, hostStr: String = "localhost", port: Int = 5432): Either[IOParseExc | PSQLException, Connection] =
    eSetts(connSettings).flatMap{ (dbName, uName, pWord) => conn(dbName.srcStr, uName.srcStr, pWord, hostStr, port) }
  
  case class PgDatabaseGen(tables: RArr[Gable]) extends PgDatabase
}
<h1 align="center">Scrum Board</h1>
<div align="center">
  
[![Unlicense License][license-shield]][license-url]
</div>
<details>
  <summary>Table of Contents</summary>
  <ol>
  </ol>
</details>

## About the project
is an extension of the [wumpus game](https://de.wikipedia.org/wiki/Wumpus-Welt) by a multi-agent environment approach with
- communication between agents
- different classes of agents with different focuses
- A*-algorithm (heuristic: utility function coupled with the Manhatten distance) as the traversal algorithm through the map

### Rough structural diagram
<img src="media/wumpusStructure.png" width="500" height="350"/>

### Build with
[![Java][Java]][Java-url]
[![SQLite][SQLite]][SQLite-url]
[![Hibernate][Hibernate]][Hibernate-url]
[![SpringBoot][SpringBoot]][SpringBoot-url]
[![Mockito][Mockito]][Mockito-url]

## Demo

## How to use
1. Clone the repository
```sh
git clone https://github.com/Zatzi08/ScrumBoard.git
cd ScrumBoard
```
2. Build the project with
```sh
mvn spring-boot:run
```
3. Go into your preferred browser to <a href="http://localhost:8080/">localhost</a> (port: 8080)

TODO: Add proper readme with explanations of the architecture, used tech stack, requirements, ...

<!-- MARKDOWN LINKS & IMAGES -->
[Java]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://www.java.com/download/ie_manual.jsp
[SQLite]: https://img.shields.io/badge/SQLite-003B57?style=for-the-badge&logo=SQLite&logoColor=white
[SQLite-url]: https://www.sqlite.org/
[Hibernate]: https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white
[Hibernate-url]: https://hibernate.org/
[SpringBoot]: https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=Spring&logoColor=white
[SpringBoot-url]: https://spring.io/projects/spring-boot
[Mockito]: https://img.shields.io/badge/Mockito-5.11.0-blue?style=for-the-badge&logoColor=white
[Mockito-url]: https://site.mockito.org/
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/Zatzi08/Wumpus/blob/main/LICENSE

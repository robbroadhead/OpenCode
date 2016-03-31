
# -----------------------------------------------------------------------
# Recipes
# -----------------------------------------------------------------------
drop table if exists Recipes;

CREATE TABLE Recipes
(
        RecipeID INTEGER NOT NULL AUTO_INCREMENT,
        Title VARCHAR (50),
        SubmittedBy VARCHAR (50),
        RecipeBody LONGBLOB NOT NULL,
        RecipeCategory VARCHAR (50) NOT NULL,
        Visible BIT NOT NULL,
        DateSubmitted DATETIME NOT NULL,
    PRIMARY KEY(RecipeID)
);

# -----------------------------------------------------------------------
# Ratings
# -----------------------------------------------------------------------
drop table if exists Ratings;

CREATE TABLE Ratings
(
        RatingID INTEGER NOT NULL AUTO_INCREMENT,
        RecipeID INTEGER NOT NULL,
        Rating INTEGER default 0 NOT NULL,
        IPAddress VARCHAR (15) NOT NULL,
        DateRated DATETIME NOT NULL,
    PRIMARY KEY(RatingID),
    FOREIGN KEY (RecipeID) REFERENCES Recipes (RecipeID)
);
  
  

NAME	=	Main

DIR_SRC	=	src

DIR_OUT	=	bin

SRCS	=	$(wildcard $(DIR_SRC)/*.java)

all:		$(NAME)

$(NAME):
			@javac -d $(DIR_OUT) -cp $(DIR_SRC) $(SRCS)
			@echo "\033[1;32mLancement de $(NAME)\033[0m"
			@java -cp $(DIR_OUT) $(NAME)

clean:
			rm -f $(wildcard $(DIR_OUT)/*.class)

re:			clean all

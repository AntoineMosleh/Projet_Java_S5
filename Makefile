NAME	=	Main

DIR_SRC	=	src

DIR_OUT	=	bin

SRCS	=	$(wildcard $(DIR_SRC)/*.java)

all:		$(NAME)

$(NAME):
			javac -d $(DIR_OUT) -cp $(DIR_SRC) $(SRCS)
			java -cp $(DIR_OUT) $(NAME)

clean:
			rm -f $(wildcard $(DIR_OUT)/*.class)

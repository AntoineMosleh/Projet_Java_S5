NAME		=	Main

DIR_SRC	=	src

DIR_OUT	=	bin

SRCS		=	$(wildcard $(DIR_SRC)/*.java)

GREEN		=	\033[1;32m

END		=	\033[0m

all:			$(NAME)

$(NAME):		$(DIR_OUT)
			@javac -d $(DIR_OUT) -cp $(DIR_SRC) $(SRCS)
			@echo "$(GREEN)Lancement de $(NAME)$(END)"
			@java -cp $(DIR_OUT) $(NAME)

$(DIR_OUT):
			@echo "$(GREEN)Création du dossier $(DIR_OUT)$(END)"
			@mkdir -p $(DIR_OUT)

clean:
			rm -f $(wildcard $(DIR_OUT)/*.class)

re:			clean all

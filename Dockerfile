FROM ruby:3.2

# Install Node.js and Yarn (required by Chirpy)
RUN apt-get update -qq && \
    apt-get install -y build-essential curl gnupg && \
    curl -fsSL https://deb.nodesource.com/setup_18.x | bash - && \
    apt-get install -y nodejs && \
    npm install -g yarn

# Set working directory
WORKDIR /srv/jekyll

# Prevents git "dubious ownership" errors
RUN git config --global --add safe.directory /srv/jekyll


# Install bundler + Jekyll (no Gemfile copied here, as volume will bring it in)
RUN gem install bundler jekyll jekyll-watch

# Default command
CMD ["sh", "-c", "bundle install && yarn install && bundle exec jekyll s --host 0.0.0.0"]

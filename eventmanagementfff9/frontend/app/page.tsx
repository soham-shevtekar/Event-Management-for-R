import { HeroScrollEvent } from "@/components/HeroScrollEvent";

export default function Home() {
  return (
    <main className="bg-black min-h-screen text-white">
      <HeroScrollEvent />
      {/* Rest of page content can go here */}
      <section className="h-screen flex items-center justify-center">
        <h2 className="text-4xl">More Content Below</h2>
      </section>
    </main>
  );
}
